import ammonite.ops._
import mainargs.main

import java.io.{BufferedReader, InputStreamReader}

@main
def main(part: Int, filename: String): Int = {
  part match {
    case 1 => Aoc.run1v2(Setup.readInput(filename))
    case 2 => Aoc.run2(Setup.readInput(filename))
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
