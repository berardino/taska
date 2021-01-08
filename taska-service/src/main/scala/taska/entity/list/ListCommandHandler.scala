package taska.entity.list

import akka.Done
import akka.persistence.typed.scaladsl.Effect
import taska.entity.list.ListCommand._
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.list.ListState.{CreatedListState, EmptySate}
import taska.entity.{CommandHandler, CommandHeader, EventWrapper}

object ListCommandHandler
    extends CommandHandler[ListCommand, ListEvent, ListState] {

  override val eventWrapper: EventWrapper[ListEvent] = ListEventWrapper

  override def onCommand(state: ListState, cmd: ListCommand)(implicit
      header: CommandHeader
  ): Reply = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateList(replyTo, boardId, title) => {
            persist(ListCreated(boardId, title))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case state: CreatedListState => {
        cmd match {
          case GetList(replyTo) => {
            Effect.none.thenReply(replyTo)(_ => state)
          }
          case ArchiveList(replyTo) => {
            persist(ListArchived())
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveList(replyTo) => {
            persist(ListUnArchived())
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateList(replyTo, updates) => {
            val events = updates.map {
              case UpdateListTitleOp(title) => {
                ListTitleUpdated(title)
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
