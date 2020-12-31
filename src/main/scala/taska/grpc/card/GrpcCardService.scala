package taska.grpc.card

import akka.util.Timeout
import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.card.CardCommand.{
  ArchiveCard,
  CreateCard,
  GetCard,
  UnArchiveCard,
  UpdateCard,
  UpdateCardCommand,
  UpdateCardDescription,
  UpdateCardTitle
}
import taska.entity.card.CardEntity
import taska.grpc.GrpcService
import taska.proto.card.CardServiceGrpc.CardService
import taska.proto.card.UpdateCardReq.{Description, Title}
import taska.proto.card.UpdateCardReq.Update.Cmd.{
  UpdateDescription,
  UpdateTitle
}
import taska.proto.card._

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

@Component
class GrpcCardService(
    entity: CardEntity
) extends CardService
    with GrpcService {

  implicit val timeout: Timeout = 5.seconds

  override def bindService: ServerServiceDefinition =
    CardServiceGrpc.bindService(this, ExecutionContext.global)

  override def create(req: CreateCardReq): Future[CreateCardRes] = {
    val id = UUID.randomUUID().toString.replace("-", "")
    entity
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

  override def archive(req: ArchiveCardReq): Future[ArchiveCardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => ArchiveCard(replyTo)
      )
      .map(_ => ArchiveCardRes())
  }

  override def unArchive(req: UnArchiveCardReq): Future[UnArchiveCardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => UnArchiveCard(replyTo)
      )
      .map(_ => UnArchiveCardRes())
  }

  override def update(req: UpdateCardReq): Future[UpdateCardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => UpdateCard(replyTo, mapToEntityUpdates(req))
      )
      .map(_ => UpdateCardRes())
  }

  override def get(req: GetCardReq): Future[GetCardRes] = {
    entity
      .runCommand(
        req.id,
        replyTo => GetCard(replyTo)
      )
      .map(res => GetCardRes(res.listId, res.title, res.description))
  }

  def mapToEntityUpdates(req: UpdateCardReq): Seq[UpdateCardCommand] = {
    req.updates.map(_.cmd).flatMap {
      case UpdateTitle(Title(title, _)) => {
        Some(UpdateCardTitle(title))
      }
      case UpdateDescription(Description(description, _)) => {
        Some(UpdateCardDescription(description))
      }
      case _ => {
        None
      }
    }
  }

}
