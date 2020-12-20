package taska.entity.board

import taska.entity.{EntityId, EntityState}
import taska.entity.board.BoardEnum.BoardStatus
import taska.entity.board.BoardEnum.BoardStatus.BoardStatus
import taska.entity.board.BoardEvent._

sealed trait BoardState extends EntityState[BoardEvent, BoardState]

object BoardState {

  case object EmptySate extends BoardState {
    override def applyEvent(event: BoardEvent): BoardState = {
      event match {
        case BoardCreated(_, entityId, title, description) => {
          CreatedBoardState(entityId, title, description)
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
      entityId: String,
      title: String,
      description: Option[String],
      lists: Seq[String] = Seq.empty,
      status: BoardStatus = BoardStatus.Active
  ) extends BoardState
      with EntityId[String] {
    override def applyEvent(event: BoardEvent): BoardState = {
      event match {
        case BoardArchived(_, _) => {
          copy(status = BoardStatus.Archived)
        }
        case BoardUnArchived(_, _) => {
          copy(status = BoardStatus.Active)
        }
        case BoardListAdded(_, _, listId) => {
          copy(lists = this.lists ++ List(listId))
        }
        case BoardTitleUpdated(_, _, name) => {
          copy(title = name)
        }
        case BoardDescriptionUpdated(_, _, description) => {
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
