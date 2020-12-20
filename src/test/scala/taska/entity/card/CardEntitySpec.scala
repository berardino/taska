package taska.entity.card

import akka.Done
import taska.entity.EventContext
import taska.entity.card.CardCommand._
import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEvent.{
  CardArchived,
  CardCreated,
  CardDescriptionUpdated,
  CardTitleUpdated,
  CardUnArchived
}
import taska.entity.card.CardState.CreatedCardState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class CardEntitySpec
    extends PersistenceSpec[CardCommand, CardEvent, CardState](
      CardEntity(Synth.genStr())
    )
    with UnitSpec {

  val entityId: String = genStr()
  val ctx: RequestContext = RequestContext()
  val evtCtx: EventContext = EventContext(ctx)
  val title: String = genStr()
  val listId: String = genStr()
  val description: Option[String] = genOptStr()

  "card" must {

    "be created with a title and optionally a description, no cards and in active state" in {
      val result =
        behaviorTestKit.runCommand(reply =>
          CreateCard(entityId, ctx, reply, listId, title, description)
        )

      result.reply should be(Done)
      result.event should be(
        CardCreated(evtCtx, entityId, listId, title, description)
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
        behaviorTestKit.runCommand(reply => ArchiveCard(ctx, reply))

      result.reply should be(Done)
      result.event should be(CardArchived(evtCtx, entityId))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchiveCard(ctx, reply))

      result.reply should be(Done)
      result.event should be(CardUnArchived(evtCtx, entityId))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Active)
      )
    }
  }

  "card title" can {
    "be updated" in {
      val newTitle = genStr()
      val result =
        behaviorTestKit.runCommand(reply =>
          UpdateCard(ctx, reply, Seq(UpdateCardTitle(newTitle)))
        )

      result.reply should be(Done)
      result.event should be(CardTitleUpdated(evtCtx, entityId, newTitle))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(title = newTitle)
      )
    }
  }

  "card description" can {
    "be updated" in {
      val newDescription = genOptStr()
      val result =
        behaviorTestKit.runCommand(reply =>
          UpdateCard(ctx, reply, Seq(UpdateCardDescription(newDescription)))
        )

      result.reply should be(Done)
      result.event should be(
        CardDescriptionUpdated(evtCtx, entityId, newDescription)
      )
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(description = newDescription)
      )
    }
  }
}
