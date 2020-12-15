package taska.entity.list

import akka.Done
import akka.actor.typed.ActorRef
import taska.cqrs.{Command, CommandReply}
import taska.request.RequestContext

sealed trait ListCommand extends Command with CommandReply[Done]

object ListCommand {

  case class Create(
      ctx: RequestContext,
      replyTo: ActorRef[Done],
      name: String
  ) extends ListCommand

  case class Archive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends ListCommand

  case class UnArchive(ctx: RequestContext, replyTo: ActorRef[Done])
      extends ListCommand

}
