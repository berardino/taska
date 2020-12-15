package taska.entity.list

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.util.Timeout
import org.springframework.stereotype.Component
import taska.entity.{EntityDefinition, EntityPersistence, EntitySharding}

import scala.concurrent.duration.DurationInt

object ListEntity
    extends EntityDefinition[ListCommand]("List")
    with EntityPersistence[ListCommand, ListEvent, ListState] {

  val emptyState = ListState.EmptySate

  val commandHandler = ListCommandHandler

  val eventHandler = ListEventHandler

  override def apply(entityId: String): Behavior[ListCommand] = {
    withEnforcedReplies(entityId)
  }
}

@Component
class ListEntitySharding(val sharding: ClusterSharding)
    extends EntitySharding[ListCommand] {
  val entityDefinition = ListEntity
  val timeout: Timeout = Timeout(3.seconds)
}
