package taska.entity.board

import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.board.BoardEvent._
import taska.entity.{Event, EventHeader, EventWrapper, PersistEventEnvelope}
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[BoardCreated], name = "created"),
    new JsonSubTypes.Type(
      value = classOf[BoardArchived],
      name = "archived"
    ),
    new JsonSubTypes.Type(
      value = classOf[BoardUnArchived],
      name = "unarchived"
    ),
    new JsonSubTypes.Type(value = classOf[BoardListAdded], name = "list_added"),
    new JsonSubTypes.Type(
      value = classOf[BoardTitleUpdated],
      name = "title_updated"
    ),
    new JsonSubTypes.Type(
      value = classOf[BoardDescriptionUpdated],
      name = "description_updated"
    )
  )
)
sealed trait BoardEvent extends Event

case class BoardEventEnvelope(header: EventHeader, event: BoardEvent)
    extends PersistEventEnvelope[BoardEvent]

object BoardEventWrapper extends EventWrapper[BoardEvent] {
  override def wrap(entityId: String, event: BoardEvent)(implicit
      ctx: RequestContext
  ): PersistEventEnvelope[BoardEvent] =
    BoardEventEnvelope(EventHeader(entityId, ctx), event)
}

object BoardEvent {

  case class BoardCreated(title: String, description: Option[String])
      extends BoardEvent

  case class BoardArchived() extends BoardEvent

  case class BoardUnArchived() extends BoardEvent

  case class BoardListAdded(listId: String) extends BoardEvent

  case class BoardTitleUpdated(title: String) extends BoardEvent

  case class BoardDescriptionUpdated(description: Option[String])
      extends BoardEvent
}
