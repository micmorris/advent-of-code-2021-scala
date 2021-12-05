import ammonite.ops._
import cats.implicits._
import mainargs.main
import monocle.Traversal
import shapeless.tag
import shapeless.tag.@@

import java.io.{BufferedReader, InputStreamReader}
import scala.annotation.tailrec

@main
def main(part: String, maybeFilename: Option[String]): Int = {
  (part, maybeFilename) match {
    case ("test", _) =>
      Test.runAllTests
    case ("p1", Some(filename)) =>
      Aoc.run1(Setup.readInput(filename))
    case ("p2", Some(filename)) =>
      Aoc.run2(Setup.readInput(filename))
    case _ =>
      println("Invalid input given.")
      -1
  }
}

object Aoc {

  import Bingo._

  def run1(input: BingoGame): Int @@ Score = {
    playGame(input.draws, input.cards)(tag[Draw](-1))._2
  }

  def run2(input: BingoGame): Int = {
    ???
  }

  @tailrec
  def playGame(draws: List[Int @@ Draw], cards: List[BingoCard])(
      implicit lastDraw: Int @@ Draw
  ): (Int @@ WinnerIndex, Int @@ Score, List[BingoCard]) = {
    (draws, findWinner(cards)) match {
      case (_, Some((score, index))) =>
        (index, score, cards)
      case (Nil, _) =>
        ???
      case (draw :: remainingDraws, _) =>
        playGame(remainingDraws, markCards(cards, draw))(draw)
    }
  }

  def markCards(cards: List[BingoCard], draw: Int @@ Draw): List[BingoCard] = {
    cards.map(card =>
      if (matrixTraversal.exist(_.contains((draw, false)))(card.rows)) {
        BingoCard(
          updateMatrixMatch(draw)(card.rows)
        )
      } else {
        card
      }
    )
  }

  def findWinner(cards: List[BingoCard])(
      implicit lastDraw: Int @@ Draw
  ): Option[(Int @@ Score, Int @@ WinnerIndex)] = {
    Option(
      cards
        .indexWhere(card =>
          card.rows.exists(_.forall(_._2)) ||
            card.columns.exists(_.forall(_._2))
        )
    )
      .filter(_ >= 0)
      .map(tag[WinnerIndex](_))
      .map(winningIndex => {
        val winningCard = cards(winningIndex)
        (tag[Score](calculateScore(winningCard)), winningIndex)
      })
  }

  def calculateScore(card: BingoCard)(
      implicit lastDraw: Int @@ Draw
  ): Int @@ Score = {
    tag[Score](
      card.rows
        .map(
          _.filterNot(_._2)
            .map(_._1)
            .sum
        )
        .sum * lastDraw
    )
  }

}

object Bingo {

  val DIMENSIONALITY = 5

  trait Draw
  trait Score
  trait WinnerIndex

  case class BingoGame(
      cards: List[BingoCard],
      draws: List[Int @@ Draw]
  )

  case class BingoCard(
      rows: List[List[(Int, Boolean)]]
  ) {
    lazy val columns = rows.transpose
  }

  private val listTraversal = Traversal.fromTraverse[List, (Int, Boolean)]

  private val updateListMatch = (n: Int) =>
    listTraversal.modify {
      case (i, false) if i == n => (i, true)
      case (i, bool)            => (i, bool)
    }

  val matrixTraversal = Traversal.fromTraverse[List, List[(Int, Boolean)]]
  val updateMatrixMatch = (n: Int) => matrixTraversal.modify(updateListMatch(n)(_))
}

object Test {

  def runAllTests: Int = {
    testThing
    0
  }

  private def testThing: Unit = {
    assert(true)
  }
}

object Setup {

  import Bingo._

  def readInput(filename: String): BingoGame = {
    val fileReader = new BufferedReader(
      new InputStreamReader(read.inputStream ! pwd / RelPath(filename))
    )
    Stream
      .continually(fileReader.readLine())
      .takeWhile(_ != null)
      .map(_.trim)
      .filter(_.nonEmpty)
      .toList match {
      case draws :: cards =>
        BingoGame(
          draws = draws
            .split(",")
            .map(_.toInt)
            .map(tag[Draw](_))
            .toList,
          cards = cards
            .grouped(DIMENSIONALITY)
            .map(rows =>
              BingoCard(
                rows = rows.map(
                  _.split("\\s+")
                    .map(_.toInt)
                    .map((_, false))
                    .toList
                )
              )
            )
            .toList
        )
    }

  }
}
