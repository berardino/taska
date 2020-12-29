package taska.entity.card

import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEnum.CardStatus.CardStatus
import taska.entity.card.CardEvent.{CardUnArchived, _}
import taska.entity.{EntityId, EntityState, EventHeader}

sealed trait CardState extends EntityState[CardEvent, CardState]

object CardState {

  case object EmptySate extends CardState {
    override def applyEvent(
        event: CardEvent
    )(implicit header: EventHeader): CardState = {
      event match {
        case CardCreated(listId, title, description) => {
          CreatedCardState(header.entityId, listId, title, description)
        }
        case _ => {
          throw new IllegalStateException(
            s"Unexpected event ${event} in state ${this}"
          )
        }
      }
    }
  }

  case class CreatedCardState(
      entityId: String,
      listId: String,
      title: String,
      description: Option[String],
      status: CardStatus = CardStatus.Active
  ) extends CardState
      with EntityId[String] {
    override def applyEvent(
        event: CardEvent
    )(implicit header: EventHeader): CardState = {
      event match {
        case CardArchived() => {
          copy(status = CardStatus.Archived)
        }
        case CardUnArchived() => {
          copy(status = CardStatus.Active)
        }
        case CardTitleUpdated(name) => {
          copy(title = name)
        }
        case CardDescriptionUpdated(description) => {
          copy(description = description)
        }
        case _ => {
          throw new IllegalStateException(
            s"Unexpected event ${event} in state ${this}"
          )
        }
      }
    }
  }

}
