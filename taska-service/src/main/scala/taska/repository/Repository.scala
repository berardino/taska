package taska.repository

import slick.basic.DatabaseConfig
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait Repository {
  val dbConfig: DatabaseConfig[JdbcProfile]

  def run[R](a: DBIO[R]): Future[R] = {
    dbConfig.db.run(a)
  }
}
