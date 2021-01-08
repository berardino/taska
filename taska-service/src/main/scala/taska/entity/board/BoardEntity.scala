package taska.entity.board

import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import org.springframework.stereotype.Component
import taska.entity.{EntityDef, EntitySharding}

object BoardEntity
    extends EntityDef[BoardCommand, BoardEvent, BoardState]("board") {

  val emptyState = BoardState.EmptySate
  val commandHandler = BoardCommandHandler
  val commandWrapper = BoardCommandWrapper
}

@Component
class BoardEntity(sharding: ClusterSharding)
    extends EntitySharding[BoardCommand, BoardEvent, BoardState](
      BoardEntity,
      sharding
    ) {}
