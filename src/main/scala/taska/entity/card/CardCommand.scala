package taska.entity.card

import akka.Done
import akka.actor.typed.ActorRef
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

}
