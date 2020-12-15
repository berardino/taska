package taska.entity.card

object CardEventHandler extends CardEntity.EventHandler {
  override def apply(state: CardState, event: CardEvent): CardState = {
    state.applyEvent(event)
  }
}
