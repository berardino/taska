package taska.grpc

import com.typesafe.config.Config
import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.{
  Server,
  ServerInterceptor,
  ServerInterceptors,
  ServerServiceDefinition
}

import java.net.InetSocketAddress

trait GrpcService {
  def bindService: ServerServiceDefinition
}

class GrpcServer(server: Server) {
  def start(): Unit = {
    server.start()
  }
}

object GrpcServer {

  def apply(
      grpcServices: Seq[GrpcService],
      grpcInterceptors: Seq[ServerInterceptor],
      config: Config
  ): GrpcServer = {
    val hostname = config.getString("hostname")
    val port = config.getInt("port")

    val serverBuilder = NettyServerBuilder
      .forAddress(new InetSocketAddress(hostname, port))
      .addService(ProtoReflectionService.newInstance())

    grpcServices
      .map { service =>
        ServerInterceptors.intercept(service.bindService, grpcInterceptors: _*)
      }
      .foreach(serverBuilder.addService)

    val server = serverBuilder.build()

    new GrpcServer(server)
  }
}
