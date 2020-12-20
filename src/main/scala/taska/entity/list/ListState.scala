package taska.entity.list

import taska.cqrs.EventState
import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEnum.ListStatus.ListStatus
import taska.entity.list.ListEvent.{Archived, Created, TitleUpdated, UnArchived}

sealed trait ListState extends EventState[ListEvent, ListState]

object ListState {

  case object EmptySate extends ListState {
    override def applyEvent(event: ListEvent): ListState = {
      event match {
        case Created(_, boardId, title) => {
          CreatedListState(boardId, title)
        }
        case _ => {
          throw new IllegalStateException(
            s"Unexpected event ${event} in state ${this}"
          )
        }
      }
    }
  }

  case class CreatedListState(
      boardId: String,
      title: String,
      cards: Seq[String] = Seq.empty,
      status: ListStatus = ListStatus.Active
  ) extends ListState {
    override def applyEvent(event: ListEvent): ListState = {
      event match {
        case Archived(_) => {
          copy(status = ListStatus.Archived)
        }
        case UnArchived(_) => {
          copy(status = ListStatus.Active)
        }
        case TitleUpdated(_, title) => {
          copy(title = title)
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
