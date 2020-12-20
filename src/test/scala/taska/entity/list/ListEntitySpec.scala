package taska.entity.list

import akka.Done
import taska.entity.EventContext
import taska.entity.list.ListCommand._
import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.list.ListState.CreatedListState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class ListEntitySpec
    extends PersistenceSpec[ListCommand, ListEvent, ListState](
      ListEntity(Synth.genStr())
    )
    with UnitSpec {

  val entityId: String = genStr()
  val ctx: RequestContext = RequestContext()
  val evtCtx: EventContext = EventContext(ctx)
  val title: String = genStr()
  val boardId: String = genStr()

  "list" must {

    "be created with a title, no cards and in open state" in {
      val result =
        behaviorTestKit.runCommand(reply =>
          CreateList(entityId, ctx, reply, boardId, title)
        )

      result.reply should be(Done)
      result.event should be(ListCreated(evtCtx, entityId, boardId, title))
      result.stateOfType[CreatedListState] should be(
        CreatedListState(entityId, boardId, title, Seq.empty, ListStatus.Active)
      )
    }
  }

  "list" can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => ArchiveList(ctx, reply))

      result.reply should be(Done)
      result.event should be(ListArchived(evtCtx, entityId))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchiveList(ctx, reply))

      result.reply should be(Done)
      result.event should be(ListUnArchived(evtCtx, entityId))
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
          UpdateList(ctx, reply, Seq(UpdateListTitle(newTitle)))
        )

      result.reply should be(Done)
      result.event should be(ListTitleUpdated(evtCtx, entityId, newTitle))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(title = newTitle)
      )
    }
  }
}
