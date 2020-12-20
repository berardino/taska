package taska.entity.card

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.entity.EventContext
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
          case CreateCard(
                entityId,
                ctx,
                replyTo,
                listId,
                title,
                description
              ) => {
            Effect
              .persist(
                CardCreated(
                  EventContext(ctx),
                  entityId,
                  listId,
                  title,
                  description
                )
              )
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedCardState(entityId, _, _, _, _) => {
        cmd match {
          case ArchiveCard(ctx, replyTo) => {
            Effect
              .persist(CardArchived(EventContext(ctx), entityId))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveCard(ctx, replyTo) => {
            Effect
              .persist(CardUnArchived(EventContext(ctx), entityId))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateCard(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateCardTitle(title) => {
                CardTitleUpdated(EventContext(ctx), entityId, title)
              }
              case UpdateCardDescription(description) => {
                CardDescriptionUpdated(EventContext(ctx), entityId, description)
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
