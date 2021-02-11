package taska.query

import org.springframework.stereotype.Component
import taska.repository.{Board, BoardRepository}
import taska.request.RequestContext

import scala.concurrent.Future

@Component
class BoardQueryService(boardRepository: BoardRepository) {
  def findAll()(implicit ctx: RequestContext): Future[Seq[Board]] = {
    boardRepository.run(boardRepository.findAll())
  }
}
