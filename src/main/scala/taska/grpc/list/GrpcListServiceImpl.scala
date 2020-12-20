package taska.grpc.list

import akka.actor.typed.ActorSystem
import akka.grpc.ServiceDescription
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import org.springframework.stereotype.Component
import taska.entity.list.ListCommand.CreateList
import taska.entity.list.ListEntitySharding
import taska.grpc.AkkaGrpcService
import taska.list.proto._
import taska.request.RequestContext

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Component
class GrpcListServiceImpl(
    entity: ListEntitySharding
)(implicit val actorSystem: ActorSystem[Nothing])
    extends GrpcListService
    with AkkaGrpcService {

  override val serviceDescription: ServiceDescription = GrpcListService

  def partial(): PartialFunction[HttpRequest, Future[HttpResponse]] = {
    GrpcListServiceHandler.partial(this)
  }

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
