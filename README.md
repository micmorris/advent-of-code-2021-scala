# AoC 2021 (Scala)

Scala implementation of AoC 2021

https://adventofcode.com/2021

## Setup

### Dependencies to Install

1. [Scala](https://www.scala-lang.org/download/)
2. [sbt](https://www.scala-sbt.org/)
3. [Ammonite](https://ammonite.io/)

### Aliases

```bash
amm-sbt='sbt Compile/fullClasspath/exportToAmmoniteScript && amm --predef target/scala-2.12/fullClasspath-Compile.sc'
```

## Quickstart

```bash
# amm-sbt scala/Aoc.sc <day> [p1 | p2 | test] [sample | input]
amm-sbt scala/Aoc.sc 1 p1 sample
```
