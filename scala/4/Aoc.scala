import ammonite.ops._
import mainargs.main

import java.io.{BufferedReader, InputStreamReader}
import scala.annotation.tailrec

val USAGE =
  """Usages:
    |  amm Aoc.scala p1 sample
    |  amm Aoc.scala p2 input
    |  amm Aoc.scala test
    |""".stripMargin

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
      println(USAGE)
      0
  }
}

object Aoc {

  import Bingo._

  //Input Outcome:
  def run1(input: BingoGame): Int = {
    playGame(input.draws, input.cards)(-1)
  }

  @tailrec
  def playGame(draws: Seq[Int], cards: Seq[BingoCard])(
      implicit lastDraw: Int
  ): Int = {
    (draws, findWinner(cards)) match {
      case (_, Some(score)) =>
        score
      case (Nil, _) =>
        -1
      case (draw :: remainingDraws, _) =>
        playGame(remainingDraws, markCards(cards, draw))(draw)
    }
  }

  def markCards(cards: Seq[BingoCard], draw: Int): Seq[BingoCard] = {
    ???
  }

  def findWinner(cards: Seq[BingoCard])(
      implicit lastDraw: Int
  ): Option[Int] = {
    ???
  }

  def calculateScore(card: BingoCard)(
      implicit lastDraw: Int
  ): Int = {
    ???
  }

  //Input Outcome:
  def run2(input: BingoGame): Int = {
    ???
  }

}

object Bingo {

  case class BingoGame(
      cards: Seq[BingoCard],
      draws: Seq[Int]
  )

  case class BingoCard(
      rows: Seq[Seq[(Int, Boolean)]]
  )
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
    val fileReader = new BufferedReader(new InputStreamReader(read.inputStream ! pwd / RelPath(filename)))
    Stream
      .continually(fileReader.readLine())
      .takeWhile(_ != null)
      .map(_.trim)
      .filter(_.nonEmpty)
      .toList match {
      case draws :: cards =>
        BingoGame(
          draws = draws.map(_.toInt),
          cards = cards
            .grouped(5)
            .toSeq
            .map(rows =>
              BingoCard(
                rows = rows.map(
                  _.split(" ")
                    .map(_.toInt)
                    .map((_, false))
                    .toSeq
                )
              )
            )
        )
    }

  }
}
