package taska.entity.board

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardState.CreatedBoardState
import taska.entity.{
  Command,
  CommandEnvelope,
  CommandHeader,
  CommandWrapper,
  ReplyTo
}
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[CreateBoard], name = "create"),
    new JsonSubTypes.Type(value = classOf[GetBoard], name = "get"),
    new JsonSubTypes.Type(value = classOf[ArchiveBoard], name = "archive"),
    new JsonSubTypes.Type(value = classOf[UnArchiveBoard], name = "unarchive"),
    new JsonSubTypes.Type(value = classOf[BoardAddList], name = "addlist"),
    new JsonSubTypes.Type(value = classOf[UpdateBoard], name = "update")
  )
)
sealed trait BoardCommand extends Command

case class BoardCommandEnvelope(header: CommandHeader, cmd: BoardCommand)
    extends CommandEnvelope[BoardCommand]

object BoardCommandWrapper extends CommandWrapper[BoardCommand] {
  override def wrap(entityId: String, cmd: BoardCommand)(implicit
      ctx: RequestContext
  ): CommandEnvelope[BoardCommand] =
    BoardCommandEnvelope(CommandHeader(entityId, ctx), cmd)
}

object BoardCommand {

  case class CreateBoard(
      replyTo: ActorRef[Done],
      title: String,
      description: Option[String] = None
  ) extends BoardCommand
      with ReplyTo[Done]

  case class GetBoard(
      replyTo: ActorRef[CreatedBoardState]
  ) extends BoardCommand
      with ReplyTo[CreatedBoardState]

  case class ArchiveBoard(
      replyTo: ActorRef[Done]
  ) extends BoardCommand
      with ReplyTo[Done]

  case class UnArchiveBoard(
      replyTo: ActorRef[Done]
  ) extends BoardCommand
      with ReplyTo[Done]

  case class BoardAddList(
      replyTo: ActorRef[Done],
      listId: String
  ) extends BoardCommand
      with ReplyTo[Done]

  case class UpdateBoard(
      replyTo: ActorRef[Done],
      updateOps: Seq[UpdateBoardOp]
  ) extends BoardCommand
      with ReplyTo[Done]

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(
        value = classOf[UpdateBoardTitleOp],
        name = "title"
      ),
      new JsonSubTypes.Type(
        value = classOf[UpdateBoardDescriptionOp],
        name = "description"
      )
    )
  )
  sealed trait UpdateBoardOp

  case class UpdateBoardTitleOp(
      title: String
  ) extends UpdateBoardOp

  case class UpdateBoardDescriptionOp(
      description: Option[String]
  ) extends UpdateBoardOp
}
