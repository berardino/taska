package taska.grpc.card

import akka.actor.typed.ActorSystem
import akka.grpc.ServiceDescription
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import org.springframework.stereotype.Component
import taska.card.proto._
import taska.entity.card.CardCommand.CreateCard
import taska.entity.card.CardEntitySharding
import taska.grpc.AkkaGrpcService
import taska.request.RequestContext

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Component
class GrpcCardServiceImpl(
    entity: CardEntitySharding
)(implicit val actorSystem: ActorSystem[Nothing])
    extends GrpcCardService
    with AkkaGrpcService {

  override val serviceDescription: ServiceDescription = GrpcCardService

  def partial(): PartialFunction[HttpRequest, Future[HttpResponse]] = {
    GrpcCardServiceHandler.partial(this)
  }

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
