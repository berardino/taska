package taska.entity.card

import taska.cqrs.EventState
import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEnum.CardStatus.CardStatus
import taska.entity.card.CardEvent.{
  Archived,
  Created,
  DescriptionUpdated,
  TitleUpdated,
  UnArchived
}

sealed trait CardState extends EventState[CardEvent, CardState]

object CardState {

  case object EmptySate extends CardState {
    override def applyEvent(event: CardEvent): CardState = {
      event match {
        case Created(_, title, description) => {
          CreatedCardState(title, description)
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
      title: String,
      description: Option[String],
      status: CardStatus = CardStatus.Active
  ) extends CardState {
    override def applyEvent(event: CardEvent): CardState = {
      event match {
        case Archived(_) => {
          copy(status = CardStatus.Archived)
        }
        case UnArchived(_) => {
          copy(status = CardStatus.Active)
        }
        case TitleUpdated(_, name) => {
          copy(title = name)
        }
        case DescriptionUpdated(_, description) => {
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
