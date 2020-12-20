package taska.entity.card

import taska.entity.{Event, EventContext}

sealed trait CardEvent extends Event[String]

object CardEvent {

  case class CardCreated(
      ctx: EventContext,
      entityId: String,
      listId: String,
      title: String,
      description: Option[String]
  ) extends CardEvent

  case class CardArchived(ctx: EventContext, entityId: String) extends CardEvent

  case class CardUnArchived(ctx: EventContext, entityId: String)
      extends CardEvent

  case class CardTitleUpdated(
      ctx: EventContext,
      entityId: String,
      title: String
  ) extends CardEvent

  case class CardDescriptionUpdated(
      ctx: EventContext,
      entityId: String,
      description: Option[String]
  ) extends CardEvent
}
