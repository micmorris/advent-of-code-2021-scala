import ammonite.ops._
import mainargs.main

import java.io.{BufferedReader, InputStreamReader}
import scala.annotation.tailrec

@main
def main(part: Int, filename: String): Int = {
  part match {
    case 0 => Test.runAllTests
    case 1 => Aoc.run1(Setup.readInput(filename))
    case 2 => Aoc.run2(Setup.readInput(filename))
  }
}

object Aoc {

  //1092896
  def run1(input: Stream[String]): Int = {
    val lines = input.toList
      .map(
        _.split("")
          .map(_.toInt)
      )

    val halfLineCount = lines.length / 2

    lines.transpose
      .map(_.sum)
      .map {
        case count if count > halfLineCount => List(1, 0)
        case count if count < halfLineCount => List(0, 1)
      }
      .transpose
      .map(_.mkString)
      .map(Integer.parseInt(_, 2)) match {
      case List(gamma, epsilon) => gamma * epsilon
    }
  }

  //4672151
  def run2(input: Stream[String]): Int = {
    val lines = input.toList
      .map(
        _.split("")
          .map(_.toInt)
          .toList
      )
    val REDUCE_OXY = true
    val REDUCE_CO2 = false
    val oxy = reduce(lines, lines.transpose, 0)(REDUCE_OXY)
    val co2 = reduce(lines, lines.transpose, 0)(REDUCE_CO2)
    println(s"Oxy: $oxy")
    println(s"CO2: $co2")
    Integer.parseInt(oxy, 2) * Integer.parseInt(co2, 2)
  }

  @tailrec
  def reduce(
      matrix: List[List[Int]],
      tMatrix: List[List[Int]],
      currIndex: Int
  )(
      implicit isMostCommon: Boolean
  ): String = {
    matrix match {
      case List(lastNum) =>
        lastNum.mkString
      case _ =>
        val commonBit = getSignificantBit(tMatrix(currIndex))
        val newMatrix = matrix.filter(_(currIndex) == commonBit)
        reduce(newMatrix, newMatrix.transpose, currIndex + 1)
    }
  }

  def getSignificantBit(bits: List[Int])(
      implicit isMostCommon: Boolean
  ): Int = {
    val halfLineCount = bits.length.toFloat / 2
    val output = (bits.sum, isMostCommon) match {
      case (count, true) if count >= halfLineCount  => 1
      case (count, true) if count < halfLineCount   => 0
      case (count, false) if count >= halfLineCount => 0
      case (count, false) if count < halfLineCount  => 1
    }
// println(s"getSignificantBit($bits)($isMostCommon); halfLineCount = $halfLineCount; output = $output")
    output
  }

}

object Test {

  def runAllTests: Int = {
    testGetSignificantBit
    0
  }

  private def testGetSignificantBit: Unit = {
    assert(Aoc.getSignificantBit(List(1, 0, 1))(true) == 1)
    assert(Aoc.getSignificantBit(List(1, 0, 0))(true) == 0)
    assert(Aoc.getSignificantBit(List(1, 0, 1, 0))(true) == 1)
    assert(Aoc.getSignificantBit(List(1, 0, 1))(false) == 0)
    assert(Aoc.getSignificantBit(List(1, 0, 0))(false) == 1)
    assert(Aoc.getSignificantBit(List(1, 0, 1, 0))(false) == 0)
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
