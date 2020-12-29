package taska.entity

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.cluster.sharding.typed.ShardingEnvelope
import akka.cluster.sharding.typed.scaladsl.{
  ClusterSharding,
  Entity,
  EntityRef,
  EntityTypeKey
}
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{
  Effect,
  EffectBuilder,
  EventSourcedBehavior,
  ReplyEffect
}
import akka.util.Timeout
import taska.request.RequestContext
import taska.serialization.CborSerializable

import scala.concurrent.Future

trait EntityId[ID] {
  val entityId: ID
}

trait ReplyTo[R] {
  val replyTo: ActorRef[R]
}

trait Command

trait Event

case class CommandHeader(entityId: String, ctx: RequestContext)
    extends EntityId[String]

trait CommandEnvelope[C <: Command] extends CborSerializable {
  val header: CommandHeader
  val cmd: C
}

case class EventHeader(entityId: String, ctx: RequestContext)
    extends EntityId[String]

trait EventEnvelope[E <: Event] extends CborSerializable {
  val header: EventHeader
  val event: E
}

trait EntityState[E, S] extends CborSerializable {
  def applyEvent(event: E)(implicit header: EventHeader): S
}

trait EntityType[C <: Command, E <: Event, S] {
  type WrappedCommand = CommandEnvelope[C]
  type WrappedEvent = EventEnvelope[E]
  type Reply = ReplyEffect[WrappedEvent, S]
  type CommandHandler =
    (S, WrappedCommand) => ReplyEffect[WrappedEvent, S]
  type ActorRefType = ActorRef[ShardingEnvelope[WrappedCommand]]
  type EntityRefType = EntityRef[WrappedCommand]
}

trait EventWrapper[E <: Event] {
  def wrap(entityId: String, cmd: E)(implicit
      ctx: RequestContext
  ): EventEnvelope[E]
}

trait CommandWrapper[C <: Command] {
  def wrap(entityId: String, cmd: C)(implicit
      ctx: RequestContext
  ): CommandEnvelope[C]
}
trait CommandHandler[C <: Command, E <: Event, S]
    extends EntityType[C, E, S]#CommandHandler
    with EntityType[C, E, S] {

  val eventWrapper: EventWrapper[E]

  def persist(event: E)(implicit
      header: CommandHeader
  ): EffectBuilder[WrappedEvent, S] = {
    Effect
      .persist(
        eventWrapper.wrap(header.entityId, event)(header.ctx)
      )
  }

  def persistAll(events: Seq[E])(implicit
      header: CommandHeader
  ): EffectBuilder[WrappedEvent, S] = {
    Effect.persist(
      events.map(eventWrapper.wrap(header.entityId, _)(header.ctx))
    )
  }

  override def apply(
      state: S,
      cmd: WrappedCommand
  ): Reply = {
    this.onCommand(state, cmd.cmd)(cmd.header)
  }

  def onCommand(
      state: S,
      cmd: C
  )(implicit
      header: CommandHeader
  ): Reply
}

abstract class EntityDef[C <: Command, E <: Event, S <: EntityState[
  E,
  S
]](name: String)
    extends EntityType[C, E, S] {
  val typeKey: EntityTypeKey[WrappedCommand] =
    EntityTypeKey[WrappedCommand](name)

  val emptyState: S
  val commandHandler: CommandHandler
  val commandWrapper: CommandWrapper[C]
  val eventHandler: (S, WrappedEvent) => S =
    (state: S, event: WrappedEvent) => {
      state.applyEvent(event.event)(event.header)
    }

  def withEnforcedReplies(entityId: String): Behavior[WrappedCommand] =
    Behaviors.setup { _ =>
      EventSourcedBehavior
        .withEnforcedReplies[WrappedCommand, WrappedEvent, S](
          persistenceId = PersistenceId.of(typeKey.name, entityId),
          emptyState = emptyState,
          commandHandler = commandHandler,
          eventHandler = eventHandler
        )
    }

  def apply(entityId: String): Behavior[WrappedCommand] = {
    withEnforcedReplies(entityId)
  }

}

abstract class EntitySharding[C <: Command, E <: Event, S <: EntityState[
  E,
  S
]](
    entity: EntityDef[C, E, S],
    sharding: ClusterSharding
) extends EntityType[C, E, S] {

  val actorRef: ActorRefType = sharding.init(
    Entity(entity.typeKey) { entityContext =>
      entity.apply(entityContext.entityId)
    }
  )

  def getEntity(entityId: String): EntityRefType = {
    sharding.entityRefFor(entity.typeKey, entityId)
  }

  def runCommand[R](
      entityId: String,
      f: ActorRef[R] => C
  )(implicit ctx: RequestContext, timeout: Timeout): Future[R] = {
    getEntity(entityId).ask(replyTo =>
      entity.commandWrapper.wrap(entityId, f(replyTo))
    )
  }
}
