package taska.entity.list

import taska.entity.{Event, EventContext}

sealed trait ListEvent extends Event[String]

object ListEvent {

  case class ListCreated(
      ctx: EventContext,
      entityId: String,
      boardId: String,
      title: String
  ) extends ListEvent

  case class ListArchived(ctx: EventContext, entityId: String) extends ListEvent

  case class ListUnArchived(ctx: EventContext, entityId: String)
      extends ListEvent

  case class ListTitleUpdated(
      ctx: EventContext,
      entityId: String,
      title: String
  ) extends ListEvent
}
