package taska.entity.board

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.cqrs.EventContext
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEvent._
import taska.entity.board.BoardState.{CreatedBoardState, EmptySate}

object BoardCommandHandler extends BoardEntity.CommandHandler {
  override def apply(
      state: BoardState,
      cmd: BoardCommand
  ): ReplyEffect[BoardEvent, BoardState] = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateBoard(ctx, replyTo, title, description) => {
            Effect
              .persist(Created(EventContext(ctx), title, description))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedBoardState(_, _, _, _) => {
        cmd match {
          case ArchiveBoard(ctx, replyTo) => {
            Effect
              .persist(Archived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveBoard(ctx, replyTo) => {
            Effect
              .persist(UnArchived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case BoardAddList(ctx, replyTo, listId) => {
            Effect
              .persist(ListAdded(EventContext(ctx), listId))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateBoard(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateBoardTitle(title) => {
                TitleUpdated(EventContext(ctx), title)
              }
              case UpdateBoardDescription(description) => {
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
