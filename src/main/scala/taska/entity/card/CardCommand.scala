package taska.entity.card

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.card.CardCommand.{
  ArchiveCard,
  CreateCard,
  UnArchiveCard,
  UpdateCard
}
import taska.entity._
import taska.request.RequestContext

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[CreateCard], name = "create"),
    new JsonSubTypes.Type(value = classOf[ArchiveCard], name = "archive"),
    new JsonSubTypes.Type(value = classOf[UnArchiveCard], name = "unarchive"),
    new JsonSubTypes.Type(value = classOf[UpdateCard], name = "update")
  )
)
sealed trait CardCommand extends Command with ReplyTo[Done]

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

  case class ArchiveCard(replyTo: ActorRef[Done]) extends CardCommand

  case class UnArchiveCard(replyTo: ActorRef[Done]) extends CardCommand

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes(
    Array(
      new JsonSubTypes.Type(value = classOf[UpdateCardTitle], name = "title"),
      new JsonSubTypes.Type(
        value = classOf[UpdateCardDescription],
        name = "description"
      )
    )
  )
  sealed trait UpdateCardCommand

  case class UpdateCardTitle(
      title: String
  ) extends UpdateCardCommand

  case class UpdateCardDescription(
      description: Option[String]
  ) extends UpdateCardCommand

  case class UpdateCard(
      replyTo: ActorRef[Done],
      updates: Seq[UpdateCardCommand]
  ) extends CardCommand

}
