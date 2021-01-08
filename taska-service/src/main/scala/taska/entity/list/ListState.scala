package taska.entity.list

import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEnum.ListStatus.ListStatus
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.{EntityState, EventHeader}

sealed trait ListState extends EntityState[ListEvent, ListState]

object ListState {

  case object EmptySate extends ListState {
    override def applyEvent(
        event: ListEvent
    )(implicit header: EventHeader): ListState = {

      event match {
        case ListCreated(boardId, title) => {
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
    override def applyEvent(
        event: ListEvent
    )(implicit header: EventHeader): ListState = {
      event match {
        case ListArchived() => {
          copy(status = ListStatus.Archived)
        }
        case ListUnArchived() => {
          copy(status = ListStatus.Active)
        }
        case ListTitleUpdated(title) => {
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
