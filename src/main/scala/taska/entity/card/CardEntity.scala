package taska.entity.card

import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import org.springframework.stereotype.Component
import taska.entity.{EntityDef, EntitySharding}

object CardEntity extends EntityDef[CardCommand, CardEvent, CardState]("card") {

  val emptyState = CardState.EmptySate
  val commandHandler = CardCommandHandler
  val commandWrapper = CardCommandWrapper
}

@Component
class CardEntity(sharding: ClusterSharding)
    extends EntitySharding[CardCommand, CardEvent, CardState](
      CardEntity,
      sharding
    ) {}
