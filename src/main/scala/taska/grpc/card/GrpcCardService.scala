package taska.grpc.card

import akka.util.Timeout
import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.card.CardCommand.CreateCard
import taska.entity.card.CardEntity
import taska.grpc.GrpcService
import taska.proto.card.CardServiceGrpc.CardService
import taska.proto.card._
import taska.request.RequestContext

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
    val entityId = UUID.randomUUID().toString
    implicit val ctx = RequestContext()
    entity
      .runCommand(
        entityId,
        replyTo =>
          CreateCard(
            replyTo,
            req.listId,
            req.title,
            req.description
          )
      )
      .map(_ => CreateCardRes())
  }

  override def archive(req: ArchiveCardReq): Future[ArchiveCardRes] = ???

  override def unArchive(req: UnArchiveCardReq): Future[UnArchiveCardRes] = ???

  override def update(req: UpdateCardReq): Future[UpdateCardRes] = ???
}
