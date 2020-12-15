package taska.grpc

import akka.actor.typed.ActorSystem
import akka.grpc.scaladsl.ServiceHandler
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.{Http, HttpConnectionContext}
import com.typesafe.config.Config

import scala.concurrent.Future

class GrpcServer(
    handlers: List[PartialFunction[HttpRequest, Future[HttpResponse]]],
    interface: String = "127.0.0.1",
    port: Int = 8080
)(implicit actorSystem: ActorSystem[Nothing]) {

  def start(): Future[Http.ServerBinding] = {
    Http(actorSystem).bindAndHandleAsync(
      ServiceHandler
        .concatOrNotFound(handlers: _*),
      interface,
      port,
      connectionContext = HttpConnectionContext()
    )
  }
}

object GrpcServer {

  def apply(
      handlers: List[PartialFunction[HttpRequest, Future[HttpResponse]]],
      config: Config
  )(implicit actorSystem: ActorSystem[Nothing]): GrpcServer = {
    val interface = config.getString("interface")
    val port = config.getInt("port")
    new GrpcServer(handlers, interface, port)
  }
}
