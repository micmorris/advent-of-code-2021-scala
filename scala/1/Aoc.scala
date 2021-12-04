import ammonite.ops._
import mainargs.main

import java.io.{BufferedReader, InputStreamReader}

@main
def main(part: Int, filename: String): Int = {
  part match {
    case 1 => Aoc.run1(filename)
    case 2 => Aoc.run2(filename)
  }
}

object Aoc {

  def run1(filename: String): Int = {
    val input = readInput(filename)
    input
      .sliding(2)
      .map {
        case Seq(a, b) if a < b => 1
        case _                  => 0
      }
      .sum
  }

  def run2(filename: String): Int = {
    val input = readInput(filename)
    input
      .sliding(3)
      .map(_.sum)
      .sliding(2)
      .map {
        case Seq(a, b) if a < b => 1
        case _                  => 0
      }
      .sum
  }

  def readInput(filename: String): Stream[Int] = {
    val fileReader = new BufferedReader(new InputStreamReader(read.inputStream ! pwd / RelPath(filename)))
    Stream
      .continually(fileReader.readLine())
      .takeWhile(_ != null)
      .map(_.trim)
      .filter(_.nonEmpty)
      .map(_.toInt)
  }
}
