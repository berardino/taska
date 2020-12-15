package taska.entity.board

import akka.Done
import taska.cqrs.EventContext
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEnum.BoardStatus
import taska.entity.board.BoardEvent._
import taska.entity.board.BoardState.CreatedBoardState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class BoardEntitySpec
    extends PersistenceSpec[BoardCommand, BoardEvent, BoardState](
      BoardEntity(Synth.genStr())
    )
    with UnitSpec {

  val ctx = RequestContext()
  val evtCtx = EventContext(ctx)

  "board" must {

    "be created with the given title and description. No lists and must not be closed." in {
      val title = genStr()
      val description = genOptStr()
      val result = behaviorTestKit.runCommand(reply =>
        Create(ctx, reply, title, description)
      )

      result.reply should be(Done)
      result.event should be(Created(evtCtx, title, description))
      result.stateOfType[CreatedBoardState] should be(
        CreatedBoardState(title, description, Seq.empty, BoardStatus.Active)
      )
    }
  }

  "board".can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => Archive(ctx, reply))

      result.reply should be(Done)
      result.event should be(Archived(evtCtx))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(status = BoardStatus.Archived)
      )
    }

    "be re-opened" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchive(ctx, reply))

      result.reply should be(Done)
      result.event should be(UnArchived(evtCtx))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(status = BoardStatus.Active)
      )
    }
  }

  "list".can {
    "be added" in {
      val listId = genStr()
      val result =
        behaviorTestKit.runCommand(reply => AddList(ctx, reply, listId))

      result.reply should be(Done)
      result.event should be(ListAdded(evtCtx, listId))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(lists = Seq(listId))
      )
    }
  }

  "board name".can {
    "be updated" in {
      val newName = genStr()
      val result =
        behaviorTestKit.runCommand(reply => UpdateName(ctx, reply, newName))

      result.reply should be(Done)
      result.event should be(NameUpdated(evtCtx, newName))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(title = newName)
      )
    }
  }

  "board description".can {
    "be updated" in {
      val newDescription = genOptStr()
      val result =
        behaviorTestKit.runCommand(reply =>
          UpdateDescription(ctx, reply, newDescription)
        )

      result.reply should be(Done)
      result.event should be(DescriptionUpdated(evtCtx, newDescription))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(description = newDescription)
      )
    }
  }
}
