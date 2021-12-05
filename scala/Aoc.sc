import $file.template.Aoc0, Aoc0.{main => main0}
import $file.one.Aoc1, Aoc1.{main => main1}
import $file.two.Aoc2, Aoc2.{main => main2}
import $file.three.Aoc3, Aoc3.{main => main3}
import $file.four.Aoc4, Aoc4.{main => main4}
import mainargs.main

//Yeah this is pretty ugly, but Ammonite doesn't offer better tools
// for dynamically importing other scripts
// https://ammonite.io/#OtherScripts
// https://github.com/com-lihaoyi/Ammonite/issues/646

val USAGE =
  """Usages:
    |# amm-sbt scala/Aoc.sc <day> [p1 | p2 | test] [sample | input]
    |  amm-sbt scala/Aoc.sc 1 p1 sample
    |  amm-sbt scala/Aoc.sc 3 p2 input
    |  amm-sbt scala/Aoc.sc 3 test
    |""".stripMargin

@main
def main(day: Int, part: String, maybeFilename: Option[String]): Int = {
  runSolution(day, part, maybeFilename)
}

def runSolution(day: Int, part: String, file: Option[String]): Int = {
  val matchingMain: (String, Option[String]) => Int = day match {
    case 0 => main0
    case 1 => main1
    case 2 => main2
    case 3 => main3
    case 4 => main4
    case _ =>
      println("Invalid day given.")
      println(USAGE)
      System.exit(1)
      ???
  }

  matchingMain(part, file.map(f => s"scala/${intToWords(day)}/$f"))
}

def intToWords(i: Int): String = i match {
  case 0 => "template"
  case 1 => "one"
  case 2 => "two"
  case 3 => "three"
  case 4 => "four"
  case 5 => "five"
}


