package taska.entity.card

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.util.Timeout
import org.springframework.stereotype.Component
import taska.entity.{EntityDefinition, EntityPersistence, EntitySharding}

import scala.concurrent.duration.DurationInt

object CardEntity
    extends EntityDefinition[CardCommand]("Card")
    with EntityPersistence[CardCommand, CardEvent, CardState] {

  val emptyState = CardState.EmptySate

  val commandHandler = CardCommandHandler

  val eventHandler = CardEventHandler

  override def apply(entityId: String): Behavior[CardCommand] = {
    withEnforcedReplies(entityId)
  }
}

@Component
class CardEntitySharding(val sharding: ClusterSharding)
    extends EntitySharding[CardCommand] {
  val entityDefinition = CardEntity
  val timeout: Timeout = Timeout(3.seconds)
}
