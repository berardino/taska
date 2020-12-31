package taska.grpc.board

import akka.util.Timeout
import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEntitySharding
import taska.grpc.GrpcService
import taska.proto.board.BoardServiceGrpc.BoardService
import taska.proto.board.UpdateBoardReq.Update.Update.{Description, Title}
import taska.proto.board.UpdateBoardReq.{UpdateDescription, UpdateTitle}
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

  override def archive(req: ArchiveBoardReq): Future[ArchiveBoardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => ArchiveBoard(replyTo)
      )
      .map(_ => ArchiveBoardRes())
  }

  override def unArchive(req: UnArchiveBoardReq): Future[UnArchiveBoardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => UnArchiveBoard(replyTo)
      )
      .map(_ => UnArchiveBoardRes())
  }

  override def update(req: UpdateBoardReq): Future[UpdateBoardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => UpdateBoard(replyTo, mapToEntityUpdates(req))
      )
      .map(_ => UpdateBoardRes())
  }

  def mapToEntityUpdates(req: UpdateBoardReq): Seq[UpdateBoardCommand] = {
    req.updates.map(_.update).flatMap {
      case Title(UpdateTitle(title, _)) => {
        Some(UpdateBoardTitle(title))
      }
      case Description(UpdateDescription(description, _)) => {
        Some(UpdateBoardDescription(description))
      }
      case _ => {
        None
      }
    }
  }
}
