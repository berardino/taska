package taska.entity.card

import akka.Done
import taska.entity.card.CardCommand._
import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEvent._
import taska.entity.card.CardState.CreatedCardState
import taska.entity.{CommandHeader, EventHeader}
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class CardEntitySpec
    extends PersistenceSpec[CardCommand, CardEvent, CardState](
      CardEntity(Synth.genStr())
    )
    with UnitSpec {

  val entityId: String = genStr()
  implicit val ctx: RequestContext = RequestContext()
  implicit val commandHeader: CommandHeader = CommandHeader(entityId, ctx)
  implicit val eventHeader: EventHeader = EventHeader(entityId, ctx)

  val title: String = genStr()
  val listId: String = genStr()
  val description: Option[String] = genOptStr()

  "card" must {

    "be created with a title and optionally a description, no cards and in active state" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          CardCommandEnvelope(
            commandHeader,
            CreateCard(reply, listId, title, description)
          )
        )

      result.reply should be(Done)
      result.event should be(
        CardEventEnvelope(eventHeader, CardCreated(listId, title, description))
      )
      result.stateOfType[CreatedCardState] should be(
        CreatedCardState(
          entityId,
          listId,
          title,
          description,
          CardStatus.Active
        )
      )
    }
  }

  "card" can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          CardCommandEnvelope(commandHeader, ArchiveCard(reply))
        )

      result.reply should be(Done)
      result.event should be(CardEventEnvelope(eventHeader, CardArchived()))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          CardCommandEnvelope(commandHeader, UnArchiveCard(reply))
        )

      result.reply should be(Done)
      result.event should be(CardEventEnvelope(eventHeader, CardUnArchived()))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Active)
      )
    }
  }

  "card title" can {
    "be updated" in {
      val newTitle = genStr()
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          CardCommandEnvelope(
            commandHeader,
            UpdateCard(reply, Seq(UpdateCardTitle(newTitle)))
          )
        )

      result.reply should be(Done)
      result.event should be(
        CardEventEnvelope(eventHeader, CardTitleUpdated(newTitle))
      )
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(title = newTitle)
      )
    }
  }

  "card description" can {
    "be updated" in {
      val newDescription = genOptStr()
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          CardCommandEnvelope(
            commandHeader,
            UpdateCard(reply, Seq(UpdateCardDescription(newDescription)))
          )
        )

      result.reply should be(Done)
      result.event should be(
        CardEventEnvelope(eventHeader, CardDescriptionUpdated(newDescription))
      )
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(description = newDescription)
      )
    }
  }
}
