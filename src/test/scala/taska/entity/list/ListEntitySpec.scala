package taska.entity.list

import akka.Done
import taska.cqrs.EventContext
import taska.entity.list.ListCommand._
import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEvent.{Archived, Created, TitleUpdated, UnArchived}
import taska.entity.list.ListState.CreatedListState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class ListEntitySpec
    extends PersistenceSpec[ListCommand, ListEvent, ListState](
      ListEntity(Synth.genStr())
    )
    with UnitSpec {

  val ctx: RequestContext = RequestContext()
  val evtCtx: EventContext = EventContext(ctx)
  val title: String = genStr()

  "list" must {

    "be created with a title, no cards and in open state" in {
      val result =
        behaviorTestKit.runCommand(reply => Create(ctx, reply, title))

      result.reply should be(Done)
      result.event should be(Created(evtCtx, title))
      result.stateOfType[CreatedListState] should be(
        CreatedListState(title, Seq.empty, ListStatus.Active)
      )
    }
  }

  "list" can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => Archive(ctx, reply))

      result.reply should be(Done)
      result.event should be(Archived(evtCtx))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchive(ctx, reply))

      result.reply should be(Done)
      result.event should be(UnArchived(evtCtx))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Active)
      )
    }
  }

  "list title" can {
    "be updated" in {
      val newTitle = genStr()
      val result =
        behaviorTestKit.runCommand(reply =>
          Update(ctx, reply, Seq(UpdateTitle(newTitle)))
        )

      result.reply should be(Done)
      result.event should be(TitleUpdated(evtCtx, newTitle))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(title = newTitle)
      )
    }
  }
}
