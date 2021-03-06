package taska.entity.list

import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import org.springframework.stereotype.Component
import taska.config.EventProcessorProps
import taska.entity.{EntityDef, EntitySharding}

object ListEntity extends EntityDef[ListCommand, ListEvent, ListState]("list") {

  val emptyState = ListState.EmptySate
  val commandHandler = ListCommandHandler
  val commandWrapper = ListCommandWrapper
}

@Component
class ListEntity(
    sharding: ClusterSharding,
    eventProcessorProps: EventProcessorProps
) extends EntitySharding[ListCommand, ListEvent, ListState](
      ListEntity,
      sharding,
      eventProcessorProps
    ) {}
