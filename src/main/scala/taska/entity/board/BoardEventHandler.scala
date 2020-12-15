package taska.entity.board

object BoardEventHandler extends BoardEntity.EventHandler {
  override def apply(state: BoardState, event: BoardEvent): BoardState = {
    state.applyEvent(event)
  }
}
