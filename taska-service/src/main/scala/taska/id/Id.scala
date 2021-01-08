package taska.id

import java.util.UUID

object Id {
  def gen(): String = {
    UUID.randomUUID().toString.replace("-", "")
  }
}
