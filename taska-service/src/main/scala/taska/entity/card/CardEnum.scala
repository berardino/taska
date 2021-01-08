package taska.entity.card

object CardEnum {

  object CardStatus extends Enumeration {
    type CardStatus = Value
    val Active, Archived = Value
  }
}
