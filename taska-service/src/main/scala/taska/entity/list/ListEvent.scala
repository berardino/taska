package taska.entity.list

import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.{Event, EventHeader, EventWrapper, PersistEventEnvelope}
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[ListCreated], name = "created"),
    new JsonSubTypes.Type(
      value = classOf[ListArchived],
      name = "archived"
    ),
    new JsonSubTypes.Type(
      value = classOf[ListUnArchived],
      name = "unarchived"
    ),
    new JsonSubTypes.Type(
      value = classOf[ListTitleUpdated],
      name = "title_updated"
    )
  )
)
sealed trait ListEvent extends Event

case class ListEventEnvelope(header: EventHeader, event: ListEvent)
    extends PersistEventEnvelope[ListEvent]

object ListEventWrapper extends EventWrapper[ListEvent] {
  override def wrap(entityId: String, event: ListEvent)(implicit
      ctx: RequestContext
  ): PersistEventEnvelope[ListEvent] =
    ListEventEnvelope(EventHeader(entityId, ctx), event)
}

object ListEvent {

  case class ListCreated(
      boardId: String,
      title: String
  ) extends ListEvent

  case class ListArchived() extends ListEvent

  case class ListUnArchived() extends ListEvent

  case class ListTitleUpdated(
      title: String
  ) extends ListEvent
}
