package taska.entity.board

import taska.entity.{Event, EventContext}

sealed trait BoardEvent extends Event[String]

object BoardEvent {

  case class BoardCreated(
      ctx: EventContext,
      entityId: String,
      title: String,
      description: Option[String]
  ) extends BoardEvent

  case class BoardArchived(entityId: String, ctx: EventContext)
      extends BoardEvent

  case class BoardUnArchived(entityId: String, ctx: EventContext)
      extends BoardEvent

  case class BoardListAdded(entityId: String, ctx: EventContext, listId: String)
      extends BoardEvent

  case class BoardTitleUpdated(
      entityId: String,
      ctx: EventContext,
      title: String
  ) extends BoardEvent

  case class BoardDescriptionUpdated(
      entityId: String,
      ctx: EventContext,
      description: Option[String]
  ) extends BoardEvent
}
