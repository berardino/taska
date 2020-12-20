package taska.entity.list

import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEnum.ListStatus.ListStatus
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.{EntityId, EntityState}

sealed trait ListState extends EntityState[ListEvent, ListState]

object ListState {

  case object EmptySate extends ListState {
    override def applyEvent(event: ListEvent): ListState = {
      event match {
        case ListCreated(_, entityId, boardId, title) => {
          CreatedListState(entityId, boardId, title)
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
      entityId: String,
      boardId: String,
      title: String,
      cards: Seq[String] = Seq.empty,
      status: ListStatus = ListStatus.Active
  ) extends ListState
      with EntityId[String] {
    override def applyEvent(event: ListEvent): ListState = {
      event match {
        case ListArchived(_, _) => {
          copy(status = ListStatus.Archived)
        }
        case ListUnArchived(_, _) => {
          copy(status = ListStatus.Active)
        }
        case ListTitleUpdated(_, _, title) => {
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
