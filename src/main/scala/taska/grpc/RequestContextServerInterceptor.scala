package taska.grpc

import io.grpc.{
  Context,
  Contexts,
  Metadata,
  ServerCall,
  ServerCallHandler,
  ServerInterceptor
}
import org.springframework.stereotype.Component
import taska.grpc.ContextKeys.RequestContextContextKey
import taska.grpc.MetadataKeys.RequestIdMetadataKey
import taska.request.RequestContext

import java.util.UUID

@Component
class RequestContextServerInterceptor extends ServerInterceptor {

  override def interceptCall[ReqT, RespT](
      call: ServerCall[ReqT, RespT],
      headers: Metadata,
      next: ServerCallHandler[ReqT, RespT]
  ): ServerCall.Listener[ReqT] = {

    val requestId = Option(headers.get(RequestIdMetadataKey))
      .map(UUID.fromString(_))
      .getOrElse(UUID.randomUUID())
    val requestContext = RequestContext(requestId)
    val ctx =
      Context.current().withValue(RequestContextContextKey, requestContext)
    Contexts.interceptCall(ctx, call, headers, next)
  }
}
