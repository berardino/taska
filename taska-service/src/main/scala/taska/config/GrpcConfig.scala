package taska.config

import com.typesafe.config.Config
import io.grpc.ServerInterceptor
import org.springframework.context.annotation.{Bean, Configuration}
import taska.grpc.{GrpcServer, GrpcService}

import scala.jdk.CollectionConverters._

@Configuration
class GrpcConfig {

  @Bean
  def grpcServer(
      grpcServices: java.util.List[GrpcService],
      grpcInterceptors: java.util.List[ServerInterceptor],
      config: Config
  ): GrpcServer = {
    GrpcServer(
      grpcServices.asScala.toSeq,
      grpcInterceptors.asScala.toSeq,
      config.getConfig("grpc.server")
    )
  }
}
