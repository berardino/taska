package taska.grpc

import io.grpc.Metadata

object MetadataKeys {
  val RequestIdMetadataKey: Metadata.Key[String] =
    Metadata.Key.of("requestId", Metadata.ASCII_STRING_MARSHALLER)
}
