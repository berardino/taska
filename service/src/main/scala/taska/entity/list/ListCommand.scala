package taska.entity.list

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.list.ListCommand.{
  ArchiveList,
  CreateList,
  GetList,
  UnArchiveList,
  UpdateList
}
import taska.entity._
import taska.entity.list.ListState.CreatedListState
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[CreateList], name = "create"),
    new JsonSubTypes.Type(value = classOf[GetList], name = "get"),
    new JsonSubTypes.Type(value = classOf[ArchiveList], name = "archive"),
    new JsonSubTypes.Type(value = classOf[UnArchiveList], name = "unarchive"),
    new JsonSubTypes.Type(value = classOf[UpdateList], name = "update")
  )
)
sealed trait ListCommand extends Command

case class ListCommandEnvelope(header: CommandHeader, cmd: ListCommand)
    extends CommandEnvelope[ListCommand]

object ListCommandWrapper extends CommandWrapper[ListCommand] {
  override def wrap(entityId: String, cmd: ListCommand)(implicit
      ctx: RequestContext
  ): CommandEnvelope[ListCommand] =
    ListCommandEnvelope(CommandHeader(entityId, ctx), cmd)
}

object ListCommand {

  case class CreateList(
      replyTo: ActorRef[Done],
      boardId: String,
      title: String
  ) extends ListCommand
      with ReplyTo[Done]

  case class GetList(replyTo: ActorRef[CreatedListState])
      extends ListCommand
      with ReplyTo[CreatedListState]

  case class ArchiveList(replyTo: ActorRef[Done])
      extends ListCommand
      with ReplyTo[Done]

  case class UnArchiveList(replyTo: ActorRef[Done])
      extends ListCommand
      with ReplyTo[Done]

  case class UpdateList(
      replyTo: ActorRef[Done],
      updates: Seq[UpdateListCommand]
  ) extends ListCommand
      with ReplyTo[Done]

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(value = classOf[UpdateListTitle], name = "title")
    )
  )
  sealed trait UpdateListCommand
  case class UpdateListTitle(
      title: String
  ) extends UpdateListCommand
}
