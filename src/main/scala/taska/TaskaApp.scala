package taska

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
    entitiesRegistry: EntityShardingRegistry,
    grpcServer: GrpcServer
) {
  entitiesRegistry.init()
  grpcServer.start()
}
