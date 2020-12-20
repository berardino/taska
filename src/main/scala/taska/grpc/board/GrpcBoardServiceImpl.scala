package taska.grpc.board

import akka.actor.typed.ActorSystem
import akka.grpc.ServiceDescription
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import org.springframework.stereotype.Component
import taska.board.proto._
import taska.entity.board.BoardCommand.CreateBoard
import taska.entity.board.BoardEntitySharding
import taska.grpc.AkkaGrpcService
import taska.request.RequestContext

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Component
class GrpcBoardServiceImpl(
    entity: BoardEntitySharding
)(implicit val actorSystem: ActorSystem[Nothing])
    extends GrpcBoardService
    with AkkaGrpcService {

  override val serviceDescription: ServiceDescription = GrpcBoardService

  def partial(): PartialFunction[HttpRequest, Future[HttpResponse]] = {
    GrpcBoardServiceHandler.partial(this)
  }

  override def create(req: CreateBoardReq): Future[CreateBoardRes] = {
    val entityId = UUID.randomUUID().toString
    val ctx = RequestContext()
    entity
      .runCommand(
        entityId,
        replyTo =>
          CreateBoard(entityId, ctx, replyTo, req.title, req.description)
      )
      .map(_ => CreateBoardRes())
  }

  override def archive(req: ArchiveBoardReq): Future[ArchiveBoardRes] = ???

  override def unArchive(req: UnArchiveBoardReq): Future[UnArchiveBoardRes] =
    ???

  override def update(req: UpdateBoardReq): Future[UpdateBoardRes] = ???
}
