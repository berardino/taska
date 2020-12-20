package taska.entity.board

import taska.cqrs.EventState
import taska.entity.board.BoardEnum.BoardStatus
import taska.entity.board.BoardEnum.BoardStatus.BoardStatus
import taska.entity.board.BoardEvent._

sealed trait BoardState extends EventState[BoardEvent, BoardState]

object BoardState {

  case object EmptySate extends BoardState {
    override def applyEvent(event: BoardEvent): BoardState = {
      event match {
        case Created(_, title, description) => {
          CreatedBoardState(title, description)
        }
        case _ => {
          throw new IllegalStateException(
            s"Unexpected event ${event} in state ${this}"
          )
        }
      }
    }
  }

  case class CreatedBoardState(
      title: String,
      description: Option[String],
      lists: Seq[String] = Seq.empty,
      status: BoardStatus = BoardStatus.Active
  ) extends BoardState {
    override def applyEvent(event: BoardEvent): BoardState = {
      event match {
        case Archived(_) => {
          copy(status = BoardStatus.Archived)
        }
        case UnArchived(_) => {
          copy(status = BoardStatus.Active)
        }
        case ListAdded(_, listId) => {
          copy(lists = this.lists ++ List(listId))
        }
        case TitleUpdated(_, name) => {
          copy(title = name)
        }
        case DescriptionUpdated(_, description) => {
          copy(description = description)
        }
        case _ => {
          throw new IllegalStateException(
            s"Unexpected event ${event} in state ${this}"
          )
        }
      }
    }
  }
}
