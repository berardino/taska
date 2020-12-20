package taska.entity.card

import akka.Done
import taska.cqrs.EventContext
import taska.entity.card.CardCommand._
import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEvent._
import taska.entity.card.CardState.CreatedCardState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class CardEntitySpec
    extends PersistenceSpec[CardCommand, CardEvent, CardState](
      CardEntity(Synth.genStr())
    )
    with UnitSpec {

  val ctx: RequestContext = RequestContext()
  val evtCtx: EventContext = EventContext(ctx)
  val title: String = genStr()
  val description: Option[String] = genOptStr()

  "card" must {

    "be created with a title and optionally a description, no cards and in active state" in {
      val result =
        behaviorTestKit.runCommand(reply =>
          Create(ctx, reply, title, description)
        )

      result.reply should be(Done)
      result.event should be(Created(evtCtx, title, description))
      result.stateOfType[CreatedCardState] should be(
        CreatedCardState(title, description, CardStatus.Active)
      )
    }
  }

  "card" can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => Archive(ctx, reply))

      result.reply should be(Done)
      result.event should be(Archived(evtCtx))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchive(ctx, reply))

      result.reply should be(Done)
      result.event should be(UnArchived(evtCtx))
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
          Update(ctx, reply, Seq(UpdateTitle(newTitle)))
        )

      result.reply should be(Done)
      result.event should be(TitleUpdated(evtCtx, newTitle))
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
          Update(ctx, reply, Seq(UpdateDescription(newDescription)))
        )

      result.reply should be(Done)
      result.event should be(DescriptionUpdated(evtCtx, newDescription))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(description = newDescription)
      )
    }
  }
}
