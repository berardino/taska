package taska.entity.card

import taska.entity.{EntityId, EntityState}
import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEnum.CardStatus.CardStatus
import taska.entity.card.CardEvent._

sealed trait CardState extends EntityState[CardEvent, CardState]

object CardState {

  case object EmptySate extends CardState {
    override def applyEvent(event: CardEvent): CardState = {
      event match {
        case CardCreated(_, entityId, listId, title, description) => {
          CreatedCardState(entityId, listId, title, description)
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
    override def applyEvent(event: CardEvent): CardState = {
      event match {
        case CardArchived(_, _) => {
          copy(status = CardStatus.Archived)
        }
        case CardUnArchived(_, _) => {
          copy(status = CardStatus.Active)
        }
        case CardTitleUpdated(_, _, name) => {
          copy(title = name)
        }
        case CardDescriptionUpdated(_, _, description) => {
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
