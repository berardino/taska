package taska.entity.board

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.cqrs.{Command, CommandReply}
import taska.request.RequestContext

sealed trait BoardCommand extends Command with CommandReply[Done]

object BoardCommand {

  case class CreateBoard(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      title: String,
      description: Option[String] = None
  ) extends BoardCommand

  case class ArchiveBoard(ctx: RequestContext, replyTo: ActorRef[Done])
      extends BoardCommand

  case class UnArchiveBoard(ctx: RequestContext, replyTo: ActorRef[Done])
      extends BoardCommand

  case class BoardAddList(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      listId: String
  ) extends BoardCommand

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(value = classOf[UpdateBoardTitle], name = "title"),
      new JsonSubTypes.Type(
        value = classOf[UpdateBoardDescription],
        name = "description"
      )
    )
  )
  sealed trait UpdateBoardCommand

  case class UpdateBoardTitle(
      title: String
  ) extends UpdateBoardCommand

  case class UpdateBoardDescription(
      description: Option[String]
  ) extends UpdateBoardCommand

  case class UpdateBoard(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      updates: Seq[UpdateBoardCommand]
  ) extends BoardCommand
}
