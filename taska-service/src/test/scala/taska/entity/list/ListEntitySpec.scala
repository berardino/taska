package taska.entity.list

import akka.Done
import akka.actor.typed.Behavior
import taska.entity.CommandEnvelope
import taska.entity.list.ListCommand._
import taska.entity.list.ListEnum.ListStatus
import taska.entity.list.ListEvent.{
  ListArchived,
  ListCreated,
  ListTitleUpdated,
  ListUnArchived
}
import taska.entity.list.ListState.CreatedListState
import taska.spec.{PersistenceSpec, UnitSpec}

class ListEntitySpec
    extends PersistenceSpec[ListCommand, ListEvent, ListState]
    with UnitSpec {

  override def behavior(
      entityId: String
  ): Behavior[CommandEnvelope[ListCommand]] = ListEntity(entityId)

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

    "retrieved" in {
      val result =
        behaviorTestKit.runCommand[CreatedListState](reply =>
          ListCommandEnvelope(commandHeader, GetList(reply))
        )
      result.reply should be(getState[CreatedListState])
    }

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
            UpdateList(reply, Seq(UpdateListTitleOp(newTitle)))
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
