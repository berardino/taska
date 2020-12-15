package taska.entity.card

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.cqrs.EventContext
import taska.entity.card.CardCommand.{Archive, Create, UnArchive}
import taska.entity.card.CardEvent.{Archived, Created, UnArchived}
import taska.entity.card.CardState.{CreatedCardState, EmptySate}

object CardCommandHandler extends CardEntity.CommandHandler {
  override def apply(
      state: CardState,
      cmd: CardCommand
  ): ReplyEffect[CardEvent, CardState] = {
    state match {
      case EmptySate => {
        cmd match {
          case Create(ctx, replyTo, title, description) => {
            Effect
              .persist(Created(EventContext(ctx), title, description))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedCardState(_, _, _) => {
        cmd match {
          case Archive(ctx, replyTo) => {
            Effect
              .persist(Archived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchive(ctx, replyTo) => {
            Effect
              .persist(UnArchived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
    }
  }
}
