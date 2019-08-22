package com.softwaremill.fixtures

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

class FixtureTraitSpec extends FlatSpec with ScalaFutures with Matchers {
  behavior of "ChocolateFactory"

  trait TestApp {
    val adService = new AdServiceMock("Best best best")
    val printer = new PackagingPrinter(adService)
    val factory = new ChocolateFactory(printer)
  }

  it should "produce small chocolate" in new TestApp {

    // When
    val chocolate = factory.produceSmall().futureValue

    // Then
    chocolate.bar shouldBe ChocolateBar(3, 3)
    chocolate.packaging.label should include(adService.slogan)
    chocolate.packaging.label should include("9 cubes")
  }

  it should "produce standard chocolate" in new TestApp {

    // When
    val chocolate = factory.produceStandard().futureValue

    // Then
    chocolate.bar shouldBe ChocolateBar(3, 5)
    chocolate.packaging.label should include(adService.slogan)
    chocolate.packaging.label should include("15 cubes")
  }

}
