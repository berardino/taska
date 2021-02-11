package taska.request

import java.time.Instant
import java.util.UUID

sealed trait Principal {
  val accountId: UUID
  val name: String
}

case class UserPrincipal(id: UUID, accountId: UUID, name: String)
    extends Principal

case class GroupPrincipal(id: UUID, accountId: UUID, name: String)
    extends Principal

case class Authentication(
    principal: Principal,
    authorities: Set[String] = Set.empty
)

case class RequestContext(
    id: UUID = UUID.randomUUID(),
    timestamp: Instant = Instant.now(),
    authentication: Option[Authentication] = None
)
