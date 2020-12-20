package taska

import org.springframework.context.annotation.{
  AnnotationConfigApplicationContext,
  ComponentScan,
  Configuration
}
import org.springframework.stereotype.Component
import taska.entity.EntityShardingRegistry
import taska.entity.board.BoardCommand.CreateBoard
import taska.entity.board.BoardEntitySharding
import taska.grpc.GrpcServer
import taska.request.RequestContext

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
    boardEntityProtocol: BoardEntitySharding,
    entitiesRegistry: EntityShardingRegistry,
    grpcServer: GrpcServer
) {
  entitiesRegistry.init()

  implicit val ctx: RequestContext = RequestContext()
  boardEntityProtocol.runCommand(
    "1",
    replyTo => CreateBoard(ctx, replyTo, "ciao", Some("desc"))
  )

  grpcServer.start()
}
