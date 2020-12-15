package taska.config

import akka.actor.typed.ActorSystem
import akka.grpc.scaladsl.ServerReflection
import com.typesafe.config.Config
import org.springframework.context.annotation.{Bean, Configuration}
import taska.grpc.{AkkaGrpcService, GrpcServer}

import scala.jdk.CollectionConverters._

@Configuration
class AkkaGrpcConfig {

  @Bean
  def grpcServer(
      grpcServices: java.util.List[AkkaGrpcService],
      config: Config
  )(implicit actorSystem: ActorSystem[Nothing]): GrpcServer = {
    val serviceHandlers = grpcServices.asScala.map(_.partial()).toList
    val serviceReflection = ServerReflection.partial(
      grpcServices.asScala.map(_.serviceDescription).toList
    )
    GrpcServer(
      serviceReflection :: serviceHandlers,
      config.getConfig("akka.grpc.server")
    )
  }
}
