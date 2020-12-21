package taska.grpc.card

import io.grpc.ServerServiceDefinition
import org.springframework.stereotype.Component
import taska.entity.card.CardCommand.CreateCard
import taska.entity.card.CardEntitySharding
import taska.grpc.GrpcService
import taska.proto.card.CardServiceGrpc.CardService
import taska.proto.card._
import taska.request.RequestContext

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

@Component
class GrpcCardService(
    entity: CardEntitySharding
) extends CardService
    with GrpcService {

  override def bindService: ServerServiceDefinition =
    CardServiceGrpc.bindService(this, ExecutionContext.global)

  override def create(req: CreateCardReq): Future[CreateCardRes] = {
    val entityId = UUID.randomUUID().toString
    val ctx = RequestContext()
    entity
      .runCommand(
        entityId,
        replyTo =>
          CreateCard(
            entityId,
            ctx,
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
