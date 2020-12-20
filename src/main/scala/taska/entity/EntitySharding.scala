package taska.entity

import akka.actor.typed.ActorRef
import akka.cluster.sharding.typed.ShardingEnvelope
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity, EntityRef}
import akka.util.Timeout

import scala.concurrent.Future

trait EntitySharding[Command] {
  val entityDefinition: EntityDefinition[Command]
  val sharding: ClusterSharding
  implicit val timeout: Timeout

  def getEntity(entityId: String): EntityRef[Command] = {
    sharding.entityRefFor(entityDefinition.typeKey, entityId)
  }

  def runCommand[C <: Command with ReplyTo[R], R](
      entityId: String,
      f: ActorRef[R] => C
  ): Future[R] = {
    getEntity(entityId).ask[R](f(_))
  }

  def init(): ActorRef[ShardingEnvelope[Command]] = {
    sharding.init(
      Entity(entityDefinition.typeKey) { entityContext =>
        entityDefinition.apply(entityContext.entityId)
      }
    )
  }
}
