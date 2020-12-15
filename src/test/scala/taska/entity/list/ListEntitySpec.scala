package taska.entity.list

import akka.Done
import taska.cqrs.EventContext
import taska.entity.list.ListCommand.{Archive, Create, UnArchive}
import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEvent.{Archived, Created, UnArchived}
import taska.entity.list.ListState.CreatedListState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class ListEntitySpec
    extends PersistenceSpec[ListCommand, ListEvent, ListState](
      ListEntity(Synth.genStr())
    )
    with UnitSpec {

  val ctx = RequestContext()
  val evtCtx = EventContext(ctx)
  val title = genStr()

  "list" must {

    "be created with the given name, no cards and must be in open state" in {
      val result =
        behaviorTestKit.runCommand(reply => Create(ctx, reply, title))

      result.reply should be(Done)
      result.event should be(Created(evtCtx, title))
      result.stateOfType[CreatedListState] should be(
        CreatedListState(title, Seq.empty, ListStatus.Active)
      )
    }
  }

  "list".can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => Archive(ctx, reply))

      result.reply should be(Done)
      result.event should be(Archived(evtCtx))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Archived)
      )
    }

    "be re-opened" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchive(ctx, reply))

      result.reply should be(Done)
      result.event should be(UnArchived(evtCtx))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Active)
      )
    }
  }
}
