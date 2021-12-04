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
    case ("p2", Some(filename)) =>
      Aoc.run2(Setup.readInput(filename))
    case _ =>
      println("Invalid input given.")
      println(USAGE)
      0
  }
}

object Aoc {

  //Input Outcome:
  def run1(input: Stream[String]): Int = {
    val lines = input.toList
      .map(
        _.split("")
          .map(_.toInt)
      )

    ???
  }

  //Input Outcome:
  def run2(input: Stream[String]): Int = {
    val lines = input.toList
      .map(
        _.split("")
          .map(_.toInt)
      )
    ???
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
