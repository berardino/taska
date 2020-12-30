package taska.entity.board

import akka.Done
import taska.entity.board.BoardCommand._
import taska.entity.board.BoardEnum.BoardStatus
import taska.entity.board.BoardEvent._
import taska.entity.board.BoardState.CreatedBoardState
import taska.entity.{CommandHeader, EventHeader}
import taska.gen.Synth
import taska.request.RequestContext
import taska.spec.{PersistenceSpec, UnitSpec}

class BoardEntitySpec
    extends PersistenceSpec[BoardCommand, BoardEvent, BoardState](
      BoardEntity(Synth.genStr())
    )
    with UnitSpec {

  val entityId: String = genStr()
  implicit val ctx: RequestContext = RequestContext()
  implicit val commandHeader: CommandHeader = CommandHeader(entityId, ctx)
  implicit val eventHeader: EventHeader = EventHeader(entityId, ctx)

  "board" must {

    "be created with a title and optionally a description,no lists and in status active" in {
      val title = genStr()
      val description = genOptStr()
      val result = behaviorTestKit.runCommand[Done](reply =>
        BoardCommandEnvelope(
          commandHeader,
          CreateBoard(reply, title, description)
        )
      )

      result.reply should be(Done)
      result.event should be(
        BoardEventEnvelope(eventHeader, BoardCreated(title, description))
      )
      result.stateOfType[CreatedBoardState] should be(
        CreatedBoardState(
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
        behaviorTestKit.runCommand[Done](reply =>
          BoardCommandEnvelope(commandHeader, ArchiveBoard(reply))
        )

      result.reply should be(Done)
      result.event should be(BoardEventEnvelope(eventHeader, BoardArchived()))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(status = BoardStatus.Archived)
      )
    }

    "be unarchived" in {
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          BoardCommandEnvelope(commandHeader, UnArchiveBoard(reply))
        )

      result.reply should be(Done)
      result.event should be(BoardEventEnvelope(eventHeader, BoardUnArchived()))
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(status = BoardStatus.Active)
      )
    }
  }

  "list" can {
    "be added" in {
      val listId = genStr()
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          BoardCommandEnvelope(commandHeader, BoardAddList(reply, listId))
        )

      result.reply should be(Done)
      result.event should be(
        BoardEventEnvelope(eventHeader, BoardListAdded(listId))
      )
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(lists = Seq(listId))
      )
    }
  }

  "board title" can {
    "be updated" in {
      val newTitle = genStr()
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          BoardCommandEnvelope(
            commandHeader,
            UpdateBoard(reply, Seq(UpdateBoardTitle(newTitle)))
          )
        )

      result.reply should be(Done)
      result.event should be(
        BoardEventEnvelope(eventHeader, BoardTitleUpdated(newTitle))
      )
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(title = newTitle)
      )
    }
  }

  "board description" can {
    "be updated" in {
      val newDescription = genOptStr()
      val result =
        behaviorTestKit.runCommand[Done](reply =>
          BoardCommandEnvelope(
            commandHeader,
            UpdateBoard(reply, Seq(UpdateBoardDescription(newDescription)))
          )
        )

      result.reply should be(Done)
      result.event should be(
        BoardEventEnvelope(eventHeader, BoardDescriptionUpdated(newDescription))
      )
      result.stateOfType[CreatedBoardState] should be(
        getState[CreatedBoardState].copy(description = newDescription)
      )
    }
  }
}
