package taska.entity.card

import akka.Done
import akka.actor.typed.ActorRef
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import taska.entity.{Command, ReplyTo}
import taska.request.RequestContext

sealed trait CardCommand extends Command with ReplyTo[Done]

object CardCommand {

  case class CreateCard(
      entityId: String,
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      listId: String,
      title: String,
      description: Option[String]
  ) extends CardCommand

  case class ArchiveCard(ctx: RequestContext, replyTo: ActorRef[Done])
      extends CardCommand

  case class UnArchiveCard(ctx: RequestContext, replyTo: ActorRef[Done])
      extends CardCommand

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
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      updates: Seq[UpdateCardCommand]
  ) extends CardCommand

}
