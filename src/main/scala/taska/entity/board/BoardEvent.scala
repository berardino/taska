package taska.entity.board

import taska.cqrs.{Event, EventContext}

sealed trait BoardEvent extends Event

sealed trait BoardUpdateEvent extends Event

object BoardEvent {

  case class Created(
      ctx: EventContext,
      title: String,
      description: Option[String]
  ) extends BoardEvent

  case class Archived(ctx: EventContext) extends BoardEvent

  case class UnArchived(ctx: EventContext) extends BoardEvent

  case class ListAdded(ctx: EventContext, listId: String) extends BoardEvent

  case class NameUpdated(ctx: EventContext, name: String) extends BoardEvent

  case class DescriptionUpdated(ctx: EventContext, description: Option[String])
      extends BoardEvent
}
