package taska.grpc

import io.grpc.Context
import taska.request.RequestContext

object ContextKeys {
  val RequestContextContextKey: Context.Key[RequestContext] =
    Context.key[RequestContext]("requestContext")
}
