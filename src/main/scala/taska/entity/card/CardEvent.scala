package taska.entity.card

import taska.cqrs.{Event, EventContext}

sealed trait CardEvent extends Event

object CardEvent {

  case class Created(
      ctx: EventContext,
      title: String,
      description: Option[String]
  ) extends CardEvent

  case class Archived(ctx: EventContext) extends CardEvent

  case class UnArchived(ctx: EventContext) extends CardEvent

}
