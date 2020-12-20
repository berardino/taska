package taska.entity.list

import akka.Done
import akka.persistence.typed.scaladsl.{Effect, ReplyEffect}
import taska.cqrs.EventContext
import taska.entity.list.ListCommand.{
  Archive,
  Create,
  UnArchive,
  Update,
  UpdateTitle
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
          case Create(ctx, replyTo, title) => {
            Effect
              .persist(Created(EventContext(ctx), title))
              .thenReply(replyTo)(_ => Done)
          }
          case _ => Effect.unhandled.thenNoReply()
        }
      }
      case CreatedListState(_, _, _) => {
        cmd match {
          case Archive(ctx, replyTo) => {
            Effect
              .persist(Archived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case UnArchive(ctx, replyTo) => {
            Effect
              .persist(UnArchived(EventContext(ctx)))
              .thenReply(replyTo)(_ => Done)
          }
          case Update(ctx, replyTo, updates) => {
            val events = updates.map {
              case UpdateTitle(title) => {
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
