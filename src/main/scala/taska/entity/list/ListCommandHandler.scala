package taska.entity.list

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.entity.EventContext
import taska.entity.list.ListCommand._
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.list.ListState.{CreatedListState, EmptySate}

object ListCommandHandler extends ListEntity.CommandHandler {
  override def apply(
      state: ListState,
      cmd: ListCommand
  ): ReplyEffect[ListEvent, ListState] = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateList(entityId, ctx, replyTo, boardId, title) => {
            Effect
              .persist(ListCreated(EventContext(ctx), entityId, boardId, title))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedListState(entityId, _, _, _, _) => {
        cmd match {
          case ArchiveList(ctx, replyTo) => {
            Effect
              .persist(ListArchived(EventContext(ctx), entityId))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveList(ctx, replyTo) => {
            Effect
              .persist(ListUnArchived(EventContext(ctx), entityId))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateList(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateListTitle(title) => {
                ListTitleUpdated(EventContext(ctx), entityId, title)
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
