package taska.cqrs

import akka.actor.typed.ActorRef
import taska.request.RequestContext
import taska.serialization.CborSerializable

trait Command extends CborSerializable {
  val ctx: RequestContext
}

trait CommandReply[R] {
  self: Command =>
  type Reply = R
  val replyTo: ActorRef[R]
}

case class EventContext(ctx: RequestContext)

trait Event extends CborSerializable {
  val ctx: EventContext
}

trait EventState[E <: Event, S] extends CborSerializable {
  def applyEvent(event: E): S
}
