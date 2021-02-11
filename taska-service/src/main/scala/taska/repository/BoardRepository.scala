package taska.repository

import com.typesafe.scalalogging.LazyLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import taska.request.RequestContext

import scala.concurrent.ExecutionContextExecutor

case class Board(id: String, title: String, description: Option[String])

@Component
class BoardRepository(
    val dbConfig: DatabaseConfig[JdbcProfile]
)(@Qualifier("dbDispatcher") implicit val ec: ExecutionContextExecutor)
    extends Repository
    with LazyLogging {
  import dbConfig.profile.api._

  private class BoardsTable(tag: Tag) extends Table[Board](tag, "boards") {
    def id: Rep[String] = column[String]("id", O.PrimaryKey)
    def title: Rep[String] = column[String]("title")
    def description: Rep[String] = column[String]("description")

    def * = (id, title, description.?) <> (Board.tupled, Board.unapply)
  }

  private val boardsTable = TableQuery[BoardsTable]

  def save(board: Board)(implicit ctx: RequestContext): DBIO[Int] = {
    logger.info(s"$ctx")
    boardsTable.insertOrUpdate(board)
  }

  def findAll()(implicit ctx: RequestContext): DBIO[Seq[Board]] = {
    logger.info(s"$ctx")
    boardsTable.result
  }
}
