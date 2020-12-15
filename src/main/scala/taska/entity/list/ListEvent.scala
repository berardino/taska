package taska.entity.list

import taska.cqrs.{Event, EventContext}

sealed trait ListEvent extends Event

object ListEvent {

  case class Created(ctx: EventContext, title: String) extends ListEvent

  case class Archived(ctx: EventContext) extends ListEvent

  case class UnArchived(ctx: EventContext) extends ListEvent

}
