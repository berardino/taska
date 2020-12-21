package taska.grpc.list

import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.list.ListCommand.CreateList
import taska.entity.list.ListEntitySharding
import taska.grpc.GrpcService
import taska.proto.list.ListServiceGrpc.ListService
import taska.proto.list._
import taska.request.RequestContext

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

@Component
class GrpcListService(
    entity: ListEntitySharding
) extends ListService
    with GrpcService {

  override def bindService: ServerServiceDefinition =
    ListServiceGrpc.bindService(this, ExecutionContext.global)

  override def create(req: CreateListReq): Future[CreateListRes] = {
    val entityId = UUID.randomUUID().toString
    val ctx = RequestContext()
    entity
      .runCommand(
        entityId,
        replyTo => CreateList(entityId, ctx, replyTo, req.boardId, req.title)
      )
      .map(_ => CreateListRes())
  }

  override def archive(req: ArchiveListReq): Future[ArchiveListRes] = ???

  override def unArchive(req: UnArchiveListReq): Future[UnArchiveListRes] = ???

  override def update(req: UpdateListReq): Future[UpdateListRes] = ???
}
