package com.softwaremill.fixtures

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

class FixtureDefSpec extends FlatSpec with ScalaFutures with Matchers {
  behavior of "ChocolateFactory"

  type Fixture = (AdServiceMock, ChocolateFactory)

  def testApp(slogan: String = "Best best best!")(test: Fixture => Unit): Unit = {
    val adService = new AdServiceMock(slogan)
    val printer = new PackagingPrinter(adService)
    val factory = new ChocolateFactory(printer)
    test((adService, factory))
  }

  it should "produce small chocolate" in testApp() { fixture =>

    // Given
    val (adService, factory) = fixture

    // When
    val chocolate = factory.produceSmall().futureValue

    // Then
    chocolate.bar shouldBe ChocolateBar(3, 3)
    chocolate.packaging.label should include(adService.slogan)
    chocolate.packaging.label should include("9 cubes")
  }

  it should "produce standard chocolate" in testApp() { fixture =>

    // Given
    val (adService, factory) = fixture

    // When
    val chocolate = factory.produceStandard().futureValue

    // Then
    chocolate.bar shouldBe ChocolateBar(3, 5)
    chocolate.packaging.label should include(adService.slogan)
    chocolate.packaging.label should include("15 cubes")
  }

}
