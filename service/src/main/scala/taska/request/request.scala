package taska.request

import java.time.Instant
import java.util.UUID

case class RequestContext(
    id: UUID = UUID.randomUUID(),
    timestamp: Instant = Instant.now()
)
