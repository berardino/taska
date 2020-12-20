package taska.entity.card

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.cqrs.EventContext
import taska.entity.card.CardCommand._
import taska.entity.card.CardEvent._
import taska.entity.card.CardState.{CreatedCardState, EmptySate}

object CardCommandHandler extends CardEntity.CommandHandler {
  override def apply(
      state: CardState,
      cmd: CardCommand
  ): ReplyEffect[CardEvent, CardState] = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateCard(ctx, replyTo, listId, title, description) => {
            Effect
              .persist(Created(EventContext(ctx), listId, title, description))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedCardState(_, _, _, _) => {
        cmd match {
          case ArchiveCard(ctx, replyTo) => {
            Effect
              .persist(Archived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveCard(ctx, replyTo) => {
            Effect
              .persist(UnArchived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateCard(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateCardTitle(title) => {
                TitleUpdated(EventContext(ctx), title)
              }
              case UpdateCardDescription(description) => {
                DescriptionUpdated(EventContext(ctx), description)
              }
            }
            Effect
              .persist(events)
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
    }
  }
}
