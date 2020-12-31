package taska.spec

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.Behavior
import akka.persistence.testkit.PersistenceTestKitPlugin
import akka.persistence.testkit.scaladsl.EventSourcedBehaviorTestKit
import akka.persistence.testkit.scaladsl.EventSourcedBehaviorTestKit.SerializationSettings
import com.typesafe.config.{Config, ConfigFactory}
import taska.entity.{
  Command,
  CommandEnvelope,
  CommandHeader,
  Event,
  EventEnvelope,
  EventHeader
}
import taska.gen.SynthLike
import taska.request.RequestContext
import taska.serialization.CborSerializable

object PersistenceSpec {
  val config: Config = ConfigFactory
    .parseString(s"""
    akka.actor.serialization-bindings {
      "${classOf[CborSerializable].getName}" = jackson-cbor
    }
    """)
    .withFallback(PersistenceTestKitPlugin.config)
}

abstract class PersistenceSpec[C <: Command, E <: Event, S](
    behavior: Behavior[CommandEnvelope[C]]
) extends ScalaTestWithActorTestKit(PersistenceSpec.config)
    with SynthLike {

  val entityId: String = genStr()
  implicit val ctx: RequestContext = RequestContext()
  implicit val commandHeader: CommandHeader = CommandHeader(entityId, ctx)
  implicit val eventHeader: EventHeader = EventHeader(entityId, ctx)

  lazy val behaviorTestKit: EventSourcedBehaviorTestKit[CommandEnvelope[
    C
  ], EventEnvelope[E], S] =
    EventSourcedBehaviorTestKit[CommandEnvelope[C], EventEnvelope[
      E
    ], S](
      system,
      behavior,
      SerializationSettings.enabled
    )

  def getState[S]: S = {
    behaviorTestKit.getState().asInstanceOf[S]
  }
}
