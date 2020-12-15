package taska.grpc

import akka.grpc.ServiceDescription
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.Future

trait AkkaGrpcService {
  val serviceDescription: ServiceDescription
  def partial(): PartialFunction[HttpRequest, Future[HttpResponse]]
}
