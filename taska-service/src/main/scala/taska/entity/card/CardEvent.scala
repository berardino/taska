package taska.entity.card

import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.card.CardEvent._
import taska.entity.{Event, EventHeader, EventWrapper, PersistEventEnvelope}
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[CardCreated], name = "created"),
    new JsonSubTypes.Type(
      value = classOf[CardArchived],
      name = "archived"
    ),
    new JsonSubTypes.Type(
      value = classOf[CardUnArchived],
      name = "unarchived"
    ),
    new JsonSubTypes.Type(
      value = classOf[CardTitleUpdated],
      name = "title_updated"
    ),
    new JsonSubTypes.Type(
      value = classOf[CardDescriptionUpdated],
      name = "description_updated"
    )
  )
)
sealed trait CardEvent extends Event

case class CardEventEnvelope(header: EventHeader, event: CardEvent)
    extends PersistEventEnvelope[CardEvent]

object CardEventWrapper extends EventWrapper[CardEvent] {
  override def wrap(entityId: String, event: CardEvent)(implicit
      ctx: RequestContext
  ): PersistEventEnvelope[CardEvent] =
    CardEventEnvelope(EventHeader(entityId, ctx), event)
}

object CardEvent {

  case class CardCreated(
      listId: String,
      title: String,
      description: Option[String]
  ) extends CardEvent

  case class CardArchived() extends CardEvent

  case class CardUnArchived() extends CardEvent

  case class CardTitleUpdated(
      title: String
  ) extends CardEvent

  case class CardDescriptionUpdated(
      description: Option[String]
  ) extends CardEvent
}
