package taska.entity.card

import akka.Done
import akka.persistence.typed.scaladsl.Effect
import taska.entity.card.CardCommand._
import taska.entity.card.CardEvent._
import taska.entity.card.CardState.{CreatedCardState, EmptySate}
import taska.entity.{CommandHandler, CommandHeader, EventWrapper}

object CardCommandHandler
    extends CommandHandler[CardCommand, CardEvent, CardState] {

  val eventWrapper: EventWrapper[CardEvent] = CardEventWrapper

  override def onCommand(state: CardState, cmd: CardCommand)(implicit
      header: CommandHeader
  ): Reply = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateCard(
                replyTo,
                listId,
                title,
                description
              ) => {
            persist(
              CardCreated(listId, title, description)
            ).thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedCardState(_, _, _, _, _) => {
        cmd match {
          case ArchiveCard(replyTo) => {
            persist(CardArchived())
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveCard(replyTo) => {
            persist(CardUnArchived())
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateCard(replyTo, updates) => {
            val events = updates.map {
              case UpdateCardTitle(title) => {
                CardTitleUpdated(title)
              }
              case UpdateCardDescription(description) => {
                CardDescriptionUpdated(description)
              }
            }
            persistAll(events)
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
    }
  }

}
