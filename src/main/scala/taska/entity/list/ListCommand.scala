package taska.entity.list

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.cqrs.{Command, CommandReply}
import taska.request.RequestContext

sealed trait ListCommand extends Command with CommandReply[Done]

object ListCommand {

  case class Create(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      title: String
  ) extends ListCommand

  case class Archive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends ListCommand

  case class UnArchive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends ListCommand

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(value = classOf[UpdateTitle], name = "title")
    )
  )
  sealed trait UpdateCommand
  case class UpdateTitle(
      title: String
  ) extends UpdateCommand

  case class Update(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      updates: Seq[UpdateCommand]
  ) extends ListCommand

}
