package taska.entity.card

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.cqrs.{Command, CommandReply}
import taska.request.RequestContext

sealed trait CardCommand extends Command with CommandReply[Done]

object CardCommand {

  case class Create(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      title: String,
      description: Option[String]
  ) extends CardCommand

  case class Archive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends CardCommand

  case class UnArchive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends CardCommand

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
  ) extends CardCommand

}
