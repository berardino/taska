package taska.entity.card

import akka.Done
import taska.cqrs.EventContext
import taska.entity.card.CardCommand.{Archive, Create, UnArchive}
import taska.entity.card.CardEnum.CardStatus
import taska.entity.card.CardEvent.{Archived, Created, UnArchived}
import taska.entity.card.CardState.CreatedCardState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class CardEntitySpec
    extends PersistenceSpec[CardCommand, CardEvent, CardState](
      CardEntity(Synth.genStr())
    )
    with UnitSpec {

  val ctx = RequestContext()
  val evtCtx = EventContext(ctx)
  val title = genStr()
  val description = genOptStr()

  "card" must {

    "be created with the given title and description. No Cards and must not be closed." in {
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

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => Archive(ctx, reply))

      result.reply should be(Done)
      result.event should be(Archived(evtCtx))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Archived)
      )
    }

    "be re-opened" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchive(ctx, reply))

      result.reply should be(Done)
      result.event should be(UnArchived(evtCtx))
      result.stateOfType[CreatedCardState] should be(
        getState[CreatedCardState].copy(status = CardStatus.Active)
      )
    }
  }
}
