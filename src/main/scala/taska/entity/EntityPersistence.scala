package taska.entity

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{EventSourcedBehavior, ReplyEffect}
import taska.request.RequestContext
import taska.serialization.CborSerializable

trait EntityId[ID] {
  val entityId: ID
}

trait ReplyTo[R] {
  val replyTo: ActorRef[R]
}

trait Command extends CborSerializable {
  val ctx: RequestContext
}

case class EventContext(ctx: RequestContext)

trait Event[ID] extends CborSerializable with EntityId[ID] {
  val entityId: ID
  val ctx: EventContext
}

trait EntityState[E <: Event[_], S] extends CborSerializable {
  def applyEvent(event: E): S
}

trait EntityPersistence[Command, Event, State] {
  self: EntityDefinition[Command] =>

  type CommandHandler = (State, Command) => ReplyEffect[Event, State]
  type EventHandler = (State, Event) => State

  val emptyState: State
  val commandHandler: CommandHandler
  val eventHandler: EventHandler

  def withEnforcedReplies(entityId: String): Behavior[Command] =
    Behaviors.setup { _ =>
      EventSourcedBehavior.withEnforcedReplies[Command, Event, State](
        persistenceId = PersistenceId.of(typeKey.name, entityId),
        emptyState = emptyState,
        commandHandler = commandHandler,
        eventHandler = eventHandler
      )
    }
}
