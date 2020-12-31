package taska.grpc.board

import akka.util.Timeout
import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.board.BoardCommand.CreateBoard
import taska.entity.board.BoardEntitySharding
import taska.grpc.GrpcService
import taska.proto.board.BoardServiceGrpc.BoardService
import taska.proto.board._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

@Component
class GrpcBoardService(
    entity: BoardEntitySharding
) extends BoardService
    with GrpcService {

  implicit val timeout: Timeout = 5.seconds

  override def bindService: ServerServiceDefinition =
    BoardServiceGrpc.bindService(this, ExecutionContext.global)

  override def create(req: CreateBoardReq): Future[CreateBoardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => CreateBoard(replyTo, req.title, req.description)
      )
      .map(_ => CreateBoardRes())
  }

  override def archive(req: ArchiveBoardReq): Future[ArchiveBoardRes] = ???

  override def unArchive(req: UnArchiveBoardReq): Future[UnArchiveBoardRes] =
    ???

  override def update(req: UpdateBoardReq): Future[UpdateBoardRes] = ???
}
