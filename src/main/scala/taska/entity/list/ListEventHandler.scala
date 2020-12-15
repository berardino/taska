package taska.entity.list

object ListEventHandler extends ListEntity.EventHandler {
  override def apply(state: ListState, event: ListEvent): ListState = {
    state.applyEvent(event)
  }
}
