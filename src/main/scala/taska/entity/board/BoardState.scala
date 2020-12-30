package taska.entity.board

import taska.entity.board.BoardEnum.BoardStatus
import taska.entity.board.BoardEnum.BoardStatus.BoardStatus
import taska.entity.board.BoardEvent.{BoardArchived, BoardUnArchived, _}
import taska.entity.{EntityState, EventHeader}

sealed trait BoardState extends EntityState[BoardEvent, BoardState]

object BoardState {

  case object EmptySate extends BoardState {
    override def applyEvent(
        event: BoardEvent
    )(implicit header: EventHeader): BoardState = {
      event match {
        case BoardCreated(title, description) => {
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
    override def applyEvent(
        event: BoardEvent
    )(implicit header: EventHeader): BoardState = {
      event match {
        case BoardArchived() => {
          copy(status = BoardStatus.Archived)
        }
        case BoardUnArchived() => {
          copy(status = BoardStatus.Active)
        }
        case BoardListAdded(listId) => {
          copy(lists = this.lists ++ List(listId))
        }
        case BoardTitleUpdated(name) => {
          copy(title = name)
        }
        case BoardDescriptionUpdated(description) => {
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
