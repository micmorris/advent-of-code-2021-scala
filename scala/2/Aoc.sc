import ammonite.ops._
import mainargs.main

import java.io.{BufferedReader, InputStreamReader}

val USAGE =
  """Usages:
    |  amm Aoc.sc p1 sample
    |  amm Aoc.sc p2 input
    |  amm Aoc.sc test
    |""".stripMargin

@main
def main(part: String, maybeFilename: Option[String]): Int = {
  (part, maybeFilename) match {
    case ("test", _) =>
      Test.runAllTests
    case ("p1", Some(filename)) =>
      Aoc.run1(Setup.readInput(filename))
    case ("p1v2", Some(filename)) =>
      Aoc.run1v2(Setup.readInput(filename))
    case ("p2", Some(filename)) =>
      Aoc.run2(Setup.readInput(filename))
    case _ =>
      println("Invalid input given.")
      println(USAGE)
      0
  }
}

object Aoc {

  def run1(input: Stream[String]): Int = {
    val (x, y) = input
      .map(_.split(" ") match {
        case Array(str, num) => (str, num.toInt)
      })
      .map {
        case ("forward", distance) => (distance, 0)
        case ("down", distance)    => (0, distance)
        case ("up", distance)      => (0, distance * -1)
      }
      .foldLeft((0, 0))((acc, i) => (acc._1 + i._1, acc._2 + i._2))
    x * y
  }

  def run1v2(input: Stream[String]): Int = {
    input
      .map(_.split(" ") match {
        case Array(str, num) => (str, num.toInt)
      })
      .map {
        case ("forward", distance) => List(distance, 0)
        case ("down", distance)    => List(0, distance)
        case ("up", distance)      => List(0, distance * -1)
      }
      .transpose match {
      case Seq(xList, yList) => xList.sum * yList.sum
    }
  }

  def run2(input: Stream[String]): Int = {
    val (x, y, aim) = input
      .map(_.split(" ") match {
        case Array(str, num) => (str, num.toInt)
      })
      .map {
        case ("forward", distance) => (distance, 0)
        case ("down", distance)    => (0, distance)
        case ("up", distance)      => (0, distance * -1)
      }
      .foldLeft((0, 0, 0))((acc, i) => (acc._1 + i._1, acc._2 + (acc._3 * i._1), acc._3 + i._2))
    x * y
  }

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

  def readInput(filename: String): Stream[String] = {
    val fileReader = new BufferedReader(new InputStreamReader(read.inputStream ! pwd / RelPath(filename)))
    Stream
      .continually(fileReader.readLine())
      .takeWhile(_ != null)
      .map(_.trim)
      .filter(_.nonEmpty)
  }
}
