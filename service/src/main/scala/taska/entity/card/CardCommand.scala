package taska.entity.card

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity._
import taska.entity.card.CardCommand._
import taska.entity.card.CardState.CreatedCardState
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[CreateCard], name = "create"),
    new JsonSubTypes.Type(value = classOf[GetCard], name = "get"),
    new JsonSubTypes.Type(value = classOf[ArchiveCard], name = "archive"),
    new JsonSubTypes.Type(value = classOf[UnArchiveCard], name = "unarchive"),
    new JsonSubTypes.Type(value = classOf[UpdateCard], name = "update")
  )
)
sealed trait CardCommand extends Command

case class CardCommandEnvelope(header: CommandHeader, cmd: CardCommand)
    extends CommandEnvelope[CardCommand]

object CardCommandWrapper extends CommandWrapper[CardCommand] {
  override def wrap(entityId: String, cmd: CardCommand)(implicit
      ctx: RequestContext
  ): CommandEnvelope[CardCommand] =
    CardCommandEnvelope(CommandHeader(entityId, ctx), cmd)
}

object CardCommand {

  case class CreateCard(
      replyTo: ActorRef[Done],
      listId: String,
      title: String,
      description: Option[String]
  ) extends CardCommand
      with ReplyTo[Done]

  case class GetCard(replyTo: ActorRef[CreatedCardState])
      extends CardCommand
      with ReplyTo[CreatedCardState]

  case class ArchiveCard(replyTo: ActorRef[Done])
      extends CardCommand
      with ReplyTo[Done]

  case class UnArchiveCard(replyTo: ActorRef[Done])
      extends CardCommand
      with ReplyTo[Done]

  case class UpdateCard(
      replyTo: ActorRef[Done],
      updateOps: Seq[UpdateCardOp]
  ) extends CardCommand
      with ReplyTo[Done]

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(value = classOf[UpdateCardTitleOp], name = "title"),
      new JsonSubTypes.Type(
        value = classOf[UpdateCardDescriptionOp],
        name = "description"
      )
    )
  )
  sealed trait UpdateCardOp

  case class UpdateCardTitleOp(
      title: String
  ) extends UpdateCardOp

  case class UpdateCardDescriptionOp(
      description: Option[String]
  ) extends UpdateCardOp

}
