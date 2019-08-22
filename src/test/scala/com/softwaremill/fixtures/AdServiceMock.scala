package com.softwaremill.fixtures

import scala.concurrent.Future

class AdServiceMock(val slogan: String) extends AdService {
  override def getSlogan(): Future[String] = Future.successful(slogan)
}
