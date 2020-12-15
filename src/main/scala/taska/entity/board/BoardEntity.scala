package taska.entity.board

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.util.Timeout
import org.springframework.stereotype.Component
import taska.entity.{EntityDefinition, EntityPersistence, EntitySharding}

import scala.concurrent.duration.DurationInt

object BoardEntity
    extends EntityDefinition[BoardCommand]("Board")
    with EntityPersistence[BoardCommand, BoardEvent, BoardState] {

  val emptyState = BoardState.EmptySate

  val commandHandler = BoardCommandHandler

  val eventHandler = BoardEventHandler

  override def apply(entityId: String): Behavior[BoardCommand] = {
    withEnforcedReplies(entityId)
  }
}

@Component
class BoardEntitySharding(val sharding: ClusterSharding)
    extends EntitySharding[BoardCommand] {
  val entityDefinition = BoardEntity
  val timeout: Timeout = Timeout(3.seconds)
}
