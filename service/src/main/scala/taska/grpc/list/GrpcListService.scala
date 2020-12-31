package taska.grpc.list

import akka.util.Timeout
import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.list.ListCommand.{
  ArchiveList,
  CreateList,
  GetList,
  UnArchiveList,
  UpdateList,
  UpdateListCommand,
  UpdateListTitle
}
import taska.entity.list.ListEntity
import taska.grpc.GrpcService
import taska.proto.list.ListServiceGrpc.ListService
import taska.proto.list.UpdateListReq.Title
import taska.proto.list.UpdateListReq.Update.Cmd.UpdateTitle
import taska.proto.list._

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

@Component
class GrpcListService(
    entity: ListEntity
) extends ListService
    with GrpcService {

  implicit val timeout: Timeout = 5.seconds

  override def bindService: ServerServiceDefinition =
    ListServiceGrpc.bindService(this, ExecutionContext.global)

  override def create(req: CreateListReq): Future[CreateListRes] = {
    val id = UUID.randomUUID().toString.replace("-", "")
    entity
      .runCommand(
        id,
        replyTo => CreateList(replyTo, req.boardId, req.title)
      )
      .map(_ => CreateListRes(id))
  }

  override def archive(req: ArchiveListReq): Future[ArchiveListRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => ArchiveList(replyTo)
      )
      .map(_ => ArchiveListRes())
  }

  override def unArchive(req: UnArchiveListReq): Future[UnArchiveListRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => UnArchiveList(replyTo)
      )
      .map(_ => UnArchiveListRes())
  }

  override def update(req: UpdateListReq): Future[UpdateListRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => UpdateList(replyTo, mapToEntityUpdates(req))
      )
      .map(_ => UpdateListRes())
  }

  override def get(req: GetListReq): Future[GetListRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => GetList(replyTo)
      )
      .map(res => GetListRes(res.boardId, res.title))

  }

  def mapToEntityUpdates(req: UpdateListReq): Seq[UpdateListCommand] = {
    req.updates.map(_.cmd).flatMap {
      case UpdateTitle(Title(title, _)) => {
        Some(UpdateListTitle(title))
      }
      case _ => {
        None
      }
    }
  }
}
