package taska.entity.board

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.entity.EventContext
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEvent.{
  BoardArchived,
  BoardCreated,
  BoardDescriptionUpdated,
  BoardListAdded,
  BoardTitleUpdated,
  BoardUnArchived
}
import taska.entity.board.BoardState.{CreatedBoardState, EmptySate}

object BoardCommandHandler extends BoardEntity.CommandHandler {
  override def apply(
      state: BoardState,
      cmd: BoardCommand
  ): ReplyEffect[BoardEvent, BoardState] = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateBoard(entityId, ctx, replyTo, title, description) => {
            Effect
              .persist(
                BoardCreated(EventContext(ctx), entityId, title, description)
              )
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedBoardState(entityId, _, _, _, _) => {
        cmd match {
          case ArchiveBoard(ctx, replyTo) => {
            Effect
              .persist(BoardArchived(entityId, EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveBoard(ctx, replyTo) => {
            Effect
              .persist(BoardUnArchived(entityId, EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case BoardAddList(ctx, replyTo, listId) => {
            Effect
              .persist(BoardListAdded(entityId, EventContext(ctx), listId))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateBoard(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateBoardTitle(title) => {
                BoardTitleUpdated(entityId, EventContext(ctx), title)
              }
              case UpdateBoardDescription(description) => {
                BoardDescriptionUpdated(
                  entityId,
                  EventContext(ctx),
                  description
                )
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
