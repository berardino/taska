package taska.entity.list

import akka.Done
import taska.entity.list.ListCommand._
import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.list.ListState.CreatedListState
import taska.entity.{CommandHeader, EventHeader}
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class ListEntitySpec
    extends PersistenceSpec[ListCommand, ListEvent, ListState](
      ListEntity(Synth.genStr())
    )
    with UnitSpec {

  val entityId: String = genStr()
  implicit val ctx: RequestContext = RequestContext()
  implicit val commandHeader: CommandHeader = CommandHeader(entityId, ctx)
  implicit val eventHeader: EventHeader = EventHeader(entityId, ctx)
  val title: String = genStr()
  val boardId: String = genStr()

  "list" must {

    "be created with a title, no cards and in open state" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          ListCommandEnvelope(commandHeader, CreateList(reply, boardId, title))
        )

      result.reply should be(Done)
      result.event should be(
        ListEventEnvelope(eventHeader, ListCreated(boardId, title))
      )
      result.stateOfType[CreatedListState] should be(
        CreatedListState(boardId, title, Seq.empty, ListStatus.Active)
      )
    }
  }

  "list" can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          ListCommandEnvelope(commandHeader, ArchiveList(reply))
        )

      result.reply should be(Done)
      result.event should be(ListEventEnvelope(eventHeader, ListArchived()))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          ListCommandEnvelope(commandHeader, UnArchiveList(reply))
        )

      result.reply should be(Done)
      result.event should be(ListEventEnvelope(eventHeader, ListUnArchived()))
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(status = ListStatus.Active)
      )
    }
  }

  "list title" can {
    "be updated" in {
      val newTitle = genStr()
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          ListCommandEnvelope(
            commandHeader,
            UpdateList(reply, Seq(UpdateListTitle(newTitle)))
          )
        )

      result.reply should be(Done)
      result.event should be(
        ListEventEnvelope(eventHeader, ListTitleUpdated(newTitle))
      )
      result.stateOfType[CreatedListState] should be(
        getState[CreatedListState].copy(title = newTitle)
      )
    }
  }
}
