package taska.entity.list

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.cqrs.EventContext
import taska.entity.list.ListCommand.{
  ArchiveList,
  CreateList,
  UnArchiveList,
  UpdateList,
  UpdateListTitle
}
import taska.entity.list.ListEvent.{Archived, Created, TitleUpdated, UnArchived}
import taska.entity.list.ListState.{CreatedListState, EmptySate}

object ListCommandHandler extends ListEntity.CommandHandler {
  override def apply(
      state: ListState,
      cmd: ListCommand
  ): ReplyEffect[ListEvent, ListState] = {
    state match {
      case EmptySate => {
        cmd match {
          case CreateList(ctx, replyTo, boardId, title) => {
            Effect
              .persist(Created(EventContext(ctx), boardId, title))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedListState(_, _, _, _) => {
        cmd match {
          case ArchiveList(ctx, replyTo) => {
            Effect
              .persist(Archived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchiveList(ctx, replyTo) => {
            Effect
              .persist(UnArchived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UpdateList(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateListTitle(title) => {
                TitleUpdated(EventContext(ctx), title)
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
