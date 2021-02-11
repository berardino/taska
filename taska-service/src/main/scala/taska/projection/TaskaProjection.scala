package taska.projection

import akka.actor.typed.ActorSystem
import akka.cluster.sharding.typed.scaladsl.ShardedDaemonProcess
import akka.persistence.jdbc.query.scaladsl.JdbcReadJournal
import akka.projection.eventsourced.scaladsl.EventSourcedProvider
import akka.projection.slick.SlickProjection
import akka.projection.{ProjectionBehavior, ProjectionId}
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import taska.config.EventProcessorProps
import taska.entity.{Event, PersistEventEnvelope}
import taska.repository.BoardRepository

import scala.concurrent.ExecutionContextExecutor
import akka.persistence.query.Offset
import akka.projection.eventsourced.EventEnvelope
import akka.projection.scaladsl.{AtLeastOnceProjection, SourceProvider}

@Component
class TaskaProjection(
    shardedDaemonProcess: ShardedDaemonProcess,
    eventProcessorProps: EventProcessorProps,
    databaseConfig: DatabaseConfig[JdbcProfile],
    boardRepository: BoardRepository
)(implicit
    val system: ActorSystem[Nothing],
    @Qualifier("dbDispatcher") ec: ExecutionContextExecutor
) {
  shardedDaemonProcess.init[ProjectionBehavior.Command](
    name = "taska-projection",
    numberOfInstances = eventProcessorProps.tags.size,
    behaviorFactory =
      (i: Int) => ProjectionBehavior(projection(eventProcessorProps.tags(i))),
    stopMessage = ProjectionBehavior.Stop
  )

  def sourceProvider(
      tag: String
  ): SourceProvider[Offset, EventEnvelope[PersistEventEnvelope[Event]]] =
    EventSourcedProvider
      .eventsByTag[PersistEventEnvelope[Event]](
        system = system,
        readJournalPluginId = JdbcReadJournal.Identifier,
        tag = tag
      )

  def projection(
      tag: String
  ): AtLeastOnceProjection[Offset, EventEnvelope[PersistEventEnvelope[Event]]] =
    SlickProjection
      .atLeastOnce(
        projectionId = ProjectionId("taska-projection", tag),
        sourceProvider(tag),
        handler = () => new TaskaProjectionHandler(boardRepository),
        databaseConfig = databaseConfig
      )
}
