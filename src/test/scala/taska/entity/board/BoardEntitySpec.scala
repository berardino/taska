package taska.entity.board

import akka.Done
import taska.entity.EventContext
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEnum.BoardStatus
import taska.entity.board.BoardEvent.{
  BoardArchived,
  BoardCreated,
  BoardDescriptionUpdated,
  BoardListAdded,
  BoardTitleUpdated,
  BoardUnArchived
}
import taska.entity.board.BoardState.CreatedBoardState
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class BoardEntitySpec
    extends PersistenceSpec[BoardCommand, BoardEvent, BoardState](
      BoardEntity(Synth.genStr())
    )
    with UnitSpec {

  val entityId: String = genStr()
  val ctx: RequestContext = RequestContext()
  val evtCtx: EventContext = EventContext(ctx)

  "board" must {

    "be created with a title and optionally a description,no lists and in status active" in {
      val title = genStr()
      val description = genOptStr()
      val result = behaviorTestKit.runCommand(reply =>
        CreateBoard(entityId, ctx, reply, title, description)
      )

      result.reply should be(Done)
      result.event should be(BoardCreated(evtCtx, entityId, title, description))
      result.stateOfType[CreatedBoardState] should be(
        CreatedBoardState(
          entityId,
          title,
          description,
          Seq.empty,
          BoardStatus.Active
        )
      )
    }
  }

  "board" can {

    "be archived" in {
      val result =
        behaviorTestKit.runCommand(reply => ArchiveBoard(ctx, reply))

      result.reply should be(Done)
      result.event should be(BoardArchived(entityId, evtCtx))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(status = BoardStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand(reply => UnArchiveBoard(ctx, reply))

      result.reply should be(Done)
      result.event should be(BoardUnArchived(entityId, evtCtx))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(status = BoardStatus.Active)
      )
    }
  }

  "list" can {
    "be added" in {
      val listId = genStr()
      val result =
        behaviorTestKit.runCommand(reply => BoardAddList(ctx, reply, listId))

      result.reply should be(Done)
      result.event should be(BoardListAdded(entityId, evtCtx, listId))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(lists = Seq(listId))
      )
    }
  }

  "board title" can {
    "be updated" in {
      val newTitle = genStr()
      val result =
        behaviorTestKit.runCommand(reply =>
          UpdateBoard(ctx, reply, Seq(UpdateBoardTitle(newTitle)))
        )

      result.reply should be(Done)
      result.event should be(BoardTitleUpdated(entityId, evtCtx, newTitle))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(title = newTitle)
      )
    }
  }

  "board description" can {
    "be updated" in {
      val newDescription = genOptStr()
      val result =
        behaviorTestKit.runCommand(reply =>
          UpdateBoard(ctx, reply, Seq(UpdateBoardDescription(newDescription)))
        )

      result.reply should be(Done)
      result.event should be(
        BoardDescriptionUpdated(entityId, evtCtx, newDescription)
      )
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(description = newDescription)
      )
    }
  }
}
