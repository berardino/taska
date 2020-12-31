package taska.entity.board

object BoardEnum {

  object BoardStatus extends Enumeration {
    type BoardStatus = Value
    val Active, Archived = Value
  }
}
