package taska.projection

import akka.Done
import akka.projection.eventsourced.EventEnvelope
import akka.projection.slick.SlickHandler
import com.typesafe.scalalogging.LazyLogging
import org.springframework.beans.factory.annotation.Qualifier
import slick.dbio.DBIO
import taska.entity.board.BoardEvent._
import taska.entity.board.BoardEventEnvelope
import taska.entity.{Event, PersistEventEnvelope}
import taska.repository.{Board, BoardRepository}

import scala.concurrent.ExecutionContextExecutor

class TaskaProjectionHandler(boardRepository: BoardRepository)(
    @Qualifier("dbDispatcher") implicit val ec: ExecutionContextExecutor
) extends SlickHandler[
      EventEnvelope[PersistEventEnvelope[Event]],
    ]
    with LazyLogging {
  override def process(
      envelope: EventEnvelope[PersistEventEnvelope[Event]]
  ): DBIO[Done] = {
    logger.info(s"${envelope.event}")
    val entityTypeId = envelope.persistenceId.split("\\|")(0)
    entityTypeId match {
      case "board" => {
        process(
          envelope.asInstanceOf[EventEnvelope[BoardEventEnvelope]]
        )
      }
    }
    DBIO.successful(Done)
  }

  def process(
      eventEnvelope: EventEnvelope[BoardEventEnvelope]
  ): Unit = {
    val BoardEventEnvelope(header, event) = eventEnvelope.event
    implicit val ctx = header.ctx
    event match {
      case BoardCreated(title, description) => {
        boardRepository.run(
          boardRepository
            .save(Board(header.entityId, title, description))
        )
      }
      case BoardArchived() => {
        DBIO.successful(Done)
      }
      case BoardUnArchived() => {
        DBIO.successful(Done)
      }
      case BoardListAdded(_) => {
        DBIO.successful(Done)
      }
      case BoardTitleUpdated(_) => {
        DBIO.successful(Done)
      }
      case BoardDescriptionUpdated(_) => {
        DBIO.successful(Done)
      }
    }
  }
}
