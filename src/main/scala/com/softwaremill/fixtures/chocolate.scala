package com.softwaremill.fixtures

import scala.concurrent.{ExecutionContext, Future}

case class ChocolateBar(x: Int, y: Int)

case class Packaging(label: String)

case class Chocolate(bar: ChocolateBar, packaging: Packaging)

trait AdService {
  def getSlogan(): Future[String]
}

class PackagingPrinter(adService: AdService)(implicit ec: ExecutionContext) {
  def print(x: Int, y: Int): Future[Packaging] =
    adService.getSlogan().map(s => Packaging(s"$s ${x * y} cubes!"))
}

class ChocolateFactory(printer: PackagingPrinter)(implicit ec: ExecutionContext) {

  def produceStandard(): Future[Chocolate] =
    pack(ChocolateBar(3, 5))

  def produceSmall(): Future[Chocolate] =
    pack(ChocolateBar(3, 3))

  private def pack(bar: ChocolateBar): Future[Chocolate] =
    printer.print(bar.x, bar.y).map(Chocolate(bar, _))
}