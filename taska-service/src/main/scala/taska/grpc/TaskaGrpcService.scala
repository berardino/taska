package taska.grpc

import akka.util.Timeout
import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEntity
import taska.entity.card.CardCommand._
import taska.entity.card.CardEntity
import taska.entity.list.ListCommand._
import taska.entity.list.ListEntity
import taska.grpc.TaskaGrpcService.toUpdateCommands
import taska.id.Id
import taska.proto.TaskaServiceGrpc.TaskaService
import taska.proto.UpdateBoardReq.Update.Cmd.{
  PUpdateBoardDescriptionCmd,
  PUpdateBoardTitleCmd
}
import taska.proto.UpdateBoardReq.{
  PUpdateBoardDescriptionOp,
  PUpdateBoardTitleOp
}
import taska.proto.UpdateCardReq.Update.Cmd.{
  PUpdateCardDescriptionCmd,
  PUpdateCardTitleCmd
}
import taska.proto.UpdateCardReq.{PUpdateCardDescriptionOp, PUpdateCardTitleOp}
import taska.proto.UpdateListReq.PUpdateListTitleOp
import taska.proto.UpdateListReq.Update.Cmd.PUpdateListTitleCmd
import taska.proto._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

object TaskaGrpcService {

  def toUpdateCommands(req: UpdateBoardReq): Seq[UpdateBoardOp] = {
    req.updates.map(_.cmd).flatMap {
      case PUpdateBoardTitleCmd(PUpdateBoardTitleOp(title, _)) => {
        Some(UpdateBoardTitleOp(title))
      }
      case PUpdateBoardDescriptionCmd(
            PUpdateBoardDescriptionOp(description, _)
          ) => {
        Some(UpdateBoardDescriptionOp(description))
      }
      case _ => {
        None
      }
    }
  }

  def toUpdateCommands(req: UpdateListReq): Seq[UpdateListOp] = {
    req.updates.map(_.cmd).flatMap {
      case PUpdateListTitleCmd(PUpdateListTitleOp(title, _)) => {
        Some(UpdateListTitleOp(title))
      }
      case _ => {
        None
      }
    }
  }

  def toUpdateCommands(req: UpdateCardReq): Seq[UpdateCardOp] = {
    req.updates.map(_.cmd).flatMap {
      case PUpdateCardTitleCmd(PUpdateCardTitleOp(title, _)) => {
        Some(UpdateCardTitleOp(title))
      }
      case PUpdateCardDescriptionCmd(
            PUpdateCardDescriptionOp(description, _)
          ) => {
        Some(UpdateCardDescriptionOp(description))
      }
      case _ => {
        None
      }
    }
  }

}

@Component
class TaskaGrpcService(
    boardEntity: BoardEntity,
    listEntity: ListEntity,
    cardEntity: CardEntity
) extends TaskaService
    with GrpcService {
  implicit val timeout: Timeout = 5.seconds

  override def bindService: ServerServiceDefinition =
    TaskaServiceGrpc.bindService(this, ExecutionContext.global)

  override def createBoard(req: CreateBoardReq): Future[CreateBoardRes] = {
    val id = Id.gen()
    boardEntity
      .runCommand(
        id,
        replyTo => CreateBoard(replyTo, req.title, req.description)
      )
      .map(_ => CreateBoardRes(id))

  }

  override def getBoard(req: GetBoardReq): Future[GetBoardRes] = {
    boardEntity
      .runCommand(
        req.id,
        replyTo => GetBoard(replyTo)
      )
      .map(res => GetBoardRes(res.title, res.description))
  }

  override def archiveBoard(req: ArchiveBoardReq): Future[ArchiveBoardRes] = {
    boardEntity
      .runCommand(
        req.id,
        replyTo => ArchiveBoard(replyTo)
      )
      .map(_ => ArchiveBoardRes())
  }

  override def unArchiveBoard(
      req: UnArchiveBoardReq
  ): Future[UnArchiveBoardRes] = {
    boardEntity
      .runCommand(
        req.id,
        replyTo => UnArchiveBoard(replyTo)
      )
      .map(_ => UnArchiveBoardRes())
  }

  override def updateBoard(req: UpdateBoardReq): Future[UpdateBoardRes] = {
    boardEntity
      .runCommand(
        req.id,
        replyTo => UpdateBoard(replyTo, toUpdateCommands(req))
      )
      .map(_ => UpdateBoardRes())
  }

  override def createList(req: CreateListReq): Future[CreateListRes] = {
    val id = Id.gen()
    listEntity
      .runCommand(
        id,
        replyTo => CreateList(replyTo, req.boardId, req.title)
      )
      .map(_ => CreateListRes(id))
  }

  override def getList(req: GetListReq): Future[GetListRes] = {
    listEntity
      .runCommand(
        req.id,
        replyTo => GetList(replyTo)
      )
      .map(res => GetListRes(res.boardId, res.title))
  }

  override def archiveList(req: ArchiveListReq): Future[ArchiveListRes] = {
    listEntity
      .runCommand(
        req.id,
        replyTo => ArchiveList(replyTo)
      )
      .map(_ => ArchiveListRes())
  }

  override def unArchiveList(
      req: UnArchiveListReq
  ): Future[UnArchiveListRes] = {
    listEntity
      .runCommand(
        req.id,
        replyTo => UnArchiveList(replyTo)
      )
      .map(_ => UnArchiveListRes())
  }

  override def updateList(req: UpdateListReq): Future[UpdateListRes] = {
    listEntity
      .runCommand(
        req.id,
        replyTo => UpdateList(replyTo, toUpdateCommands(req))
      )
      .map(_ => UpdateListRes())
  }

  override def createCard(req: CreateCardReq): Future[CreateCardRes] = {
    val id = Id.gen()
    cardEntity
      .runCommand(
        id,
        replyTo =>
          CreateCard(
            replyTo,
            req.listId,
            req.title,
            req.description
          )
      )
      .map(_ => CreateCardRes(id))
  }

  override def getCard(req: GetCardReq): Future[GetCardRes] = {
    cardEntity
      .runCommand(
        req.id,
        replyTo => GetCard(replyTo)
      )
      .map(res => GetCardRes(res.listId, res.title, res.description))
  }

  override def archiveCard(req: ArchiveCardReq): Future[ArchiveCardRes] = {
    cardEntity
      .runCommand(
        req.id,
        replyTo => ArchiveCard(replyTo)
      )
      .map(_ => ArchiveCardRes())
  }

  override def unArchiveCard(
      req: UnArchiveCardReq
  ): Future[UnArchiveCardRes] = {
    cardEntity
      .runCommand(
        req.id,
        replyTo => UnArchiveCard(replyTo)
      )
      .map(_ => UnArchiveCardRes())
  }

  override def updateCard(req: UpdateCardReq): Future[UpdateCardRes] = {
    cardEntity
      .runCommand(
        req.id,
        replyTo => UpdateCard(replyTo, toUpdateCommands(req))
      )
      .map(_ => UpdateCardRes())
  }
}
