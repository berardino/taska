package taska.entity.board

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.cqrs.{Command, CommandReply}
import taska.request.RequestContext

sealed trait BoardCommand extends Command with CommandReply[Done]

object BoardCommand {

  case class Create(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      title: String,
      description: Option[String] = None
  ) extends BoardCommand

  case class Archive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends BoardCommand

  case class UnArchive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends BoardCommand

  case class AddList(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      listId: String
  ) extends BoardCommand

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(value = classOf[UpdateTitle], name = "title"),
      new JsonSubTypes.Type(
        value = classOf[UpdateDescription],
        name = "description"
      )
    )
  )
  sealed trait UpdateCommand

  case class UpdateTitle(
      title: String
  ) extends UpdateCommand

  case class UpdateDescription(
      description: Option[String]
  ) extends UpdateCommand

  case class Update(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      updates: Seq[UpdateCommand]
  ) extends BoardCommand
}
