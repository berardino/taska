package taska

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.{
  AnnotationConfigApplicationContext,
  ComponentScan,
  Configuration
}
import org.springframework.stereotype.Component
import taska.entity.EntityShardingRegistry
import taska.grpc.GrpcServer

object ListEntity {}

object CardEntity {}

@Configuration
@ComponentScan
class TaskaApp

object TaskaApp extends App {
  val ctx = new AnnotationConfigApplicationContext(classOf[TaskaApp])
}

@Component
class TaskaEntryPoint(
    flyway: Flyway,
    entitiesRegistry: EntityShardingRegistry,
    grpcServer: GrpcServer
) {
  flyway.migrate()
  entitiesRegistry.init()
  grpcServer.start()
}
