package taska.entity.board

import akka.Done
import akka.persistence.typed.scaladsl.Effect
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEvent._
import taska.entity.board.BoardState.{CreatedBoardState, EmptySate}
import taska.entity.{CommandHandler, CommandHeader, EventWrapper}

object BoardCommandHandler
    extends CommandHandler[BoardCommand, BoardEvent, BoardState] {

  override val eventWrapper: EventWrapper[BoardEvent] = BoardEventWrapper

  override def onCommand(state: BoardState, cmd: BoardCommand)(implicit
      header: CommandHeader
  ): Reply = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateBoard(replyTo, title, description) => {
            persist(
              BoardCreated(title, description)
            ).thenReply[Done](replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case _: CreatedBoardState => {
        cmd match {
          case ArchiveBoard(replyTo) => {
            persist(BoardArchived())
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveBoard(replyTo) => {
            persist(BoardUnArchived())
              .thenReply(replyTo)(_ => Done)
          }
          case BoardAddList(replyTo, listId) => {
            persist(BoardListAdded(listId))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateBoard(replyTo, updates) => {
            val events = updates.map {
              case UpdateBoardTitle(title) => {
                BoardTitleUpdated(title)
              }
              case UpdateBoardDescription(description) => {
                BoardDescriptionUpdated(
                  description
                )
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
