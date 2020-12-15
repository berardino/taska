package taska.grpc.board

import akka.actor.typed.ActorSystem
import akka.grpc.ServiceDescription
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import org.springframework.stereotype.Component
import taska.board.proto._
import taska.grpc.AkkaGrpcService

import scala.concurrent.Future

@Component
class GrpcTaskaServiceImpl(
    //boardSharding: BoardEntitySharding
)(implicit val actorSystem: ActorSystem[Nothing])
    extends GrpcTaskaService
    with AkkaGrpcService {

  override val serviceDescription: ServiceDescription = GrpcTaskaService

  def partial(): PartialFunction[HttpRequest, Future[HttpResponse]] = {
    GrpcTaskaServiceHandler.partial(this)
  }

  override def createBoard(in: CreateBoardReq): Future[CreateBoardRes] = ???

  override def archiveBoard(in: ArchiveBoardReq): Future[ArchiveBoardRes] = ???

  override def reOpenBoard(in: ReOpenBoardReq): Future[ReOpenBoardRes] = ???

  override def createList(in: CreateListReq): Future[CreateListRes] = ???

  override def archiveList(in: ArchiveListReq): Future[ArchiveListRes] = ???

  override def reOpenList(in: ReOpenListReq): Future[ReOpenListRes] = ???

  override def createCard(in: CreateCardReq): Future[CreateCardRes] = ???

  override def archiveCard(in: ArchiveCardReq): Future[ArchiveCardRes] = ???

  override def reOpenCard(in: ReOpenCardReq): Future[ReOpenCardRes] = ???

}
