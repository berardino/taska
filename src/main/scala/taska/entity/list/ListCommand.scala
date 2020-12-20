package taska.entity.list

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.{Command, EntityId, ReplyTo}
import taska.request.RequestContext

sealed trait ListCommand extends Command with ReplyTo[Done]

object ListCommand {

  case class CreateList(
      entityId: String,
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      boardId: String,
      title: String
  ) extends ListCommand
      with EntityId[String]

  case class ArchiveList(ctx: RequestContext, replyTo: ActorRef[Done])
      extends ListCommand

  case class UnArchiveList(ctx: RequestContext, replyTo: ActorRef[Done])
      extends ListCommand

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

  case class UpdateList(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      updates: Seq[UpdateListCommand]
  ) extends ListCommand

}
