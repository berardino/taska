package taska.entity

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{EventSourcedBehavior, ReplyEffect}

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
