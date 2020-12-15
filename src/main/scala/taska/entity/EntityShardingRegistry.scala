package taska.entity

class EntityShardingRegistry(entities: List[EntitySharding[_]]) {
  def init(): Unit = {
    entities.foreach(_.init())
  }
}
