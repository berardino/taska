package taska.entity

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.{EntityRef, EntityTypeKey}

import scala.reflect.ClassTag

abstract class EntityDefinition[Command: ClassTag](val name: String) {
  type CommandType = Command
  type EntityRefType = EntityRef[Command]
  type TypeKey = EntityTypeKey[Command]

  val typeKey: TypeKey = EntityTypeKey[Command](name)

  def apply(entityId: String): Behavior[Command]
}
