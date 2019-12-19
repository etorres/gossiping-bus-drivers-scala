package es.eriktorr.katas

object BusRoute {
  private val MaxStops = 480

  def isBeforeTheEndOfTheDay(stopCount: Int): Boolean = stopCount <= BusRoute.MaxStops

  def isAfterTheEndOfTheDay(minute: Int): Boolean = {
    val stopCount = stopCountFrom(minute)
    !isBeforeTheEndOfTheDay(stopCount)
  }

  private def stopCountFrom(minute: Int): Int = minute
}

case class BusRoute(stops: Seq[Int])
