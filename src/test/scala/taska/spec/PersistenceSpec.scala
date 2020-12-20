package taska.spec

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.Behavior
import akka.persistence.testkit.PersistenceTestKitPlugin
import akka.persistence.testkit.scaladsl.EventSourcedBehaviorTestKit
import akka.persistence.testkit.scaladsl.EventSourcedBehaviorTestKit.SerializationSettings
import com.typesafe.config.ConfigFactory
import taska.cqrs.{Command, Event}
import taska.serialization.CborSerializable
import com.typesafe.config.Config

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
    behavior: Behavior[C]
) extends ScalaTestWithActorTestKit(PersistenceSpec.config) {

  lazy val behaviorTestKit: EventSourcedBehaviorTestKit[C, E, S] =
    EventSourcedBehaviorTestKit[C, E, S](
      system,
      behavior,
      SerializationSettings.enabled
    )

  def getState[S]: S = {
    behaviorTestKit.getState().asInstanceOf[S]
  }
}
