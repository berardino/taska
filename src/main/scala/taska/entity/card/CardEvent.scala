package taska.entity.card

import taska.cqrs.{Event, EventContext}

sealed trait CardEvent extends Event

object CardEvent {

  case class Created(
      ctx: EventContext,
      listId: String,
      title: String,
      description: Option[String]
  ) extends CardEvent

  case class Archived(ctx: EventContext) extends CardEvent

  case class UnArchived(ctx: EventContext) extends CardEvent

  case class TitleUpdated(ctx: EventContext, title: String) extends CardEvent

  case class DescriptionUpdated(ctx: EventContext, description: Option[String])
      extends CardEvent
}
