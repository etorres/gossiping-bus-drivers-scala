package es.eriktorr.katas

import scala.annotation.tailrec

class GossipsBroadcaster {

  private val MaxStops = 480

  def stopsToSpreadAllGossips(busRoutes: Seq[BusRoute]): String = {
    val drivers = busRoutes.zipWithIndex.map {
      case (busRoute, index) => Driver(busRoute, Set(Gossip(index)))
    }
    val stops = stopsToSpreadAllGossips(minute = 1, drivers)
    if (stops <= MaxStops) stops.toString else "never"
  }

  @tailrec
  private def stopsToSpreadAllGossips(minute: Int, drivers: Seq[Driver]): Int = if (minute > MaxStops) minute else {
    val driversAfterTheExchange = driversAfterExchangingGossipsAtCurrent(minute, drivers)
    if (haveAllTheGossips(driversAfterTheExchange)) minute else stopsToSpreadAllGossips(minute + 1, driversAfterTheExchange)
  }

  private def haveAllTheGossips(drivers: Seq[Driver]): Boolean = !drivers.exists(_.gossips.size != drivers.size)

  private def driversAfterExchangingGossipsAtCurrent(minute: Int, drivers: Seq[Driver]): Seq[Driver] = {
    drivers.groupBy(_.route.stopAt(minute)).flatMap {
      case (_, driversAtSameStop) =>
        val exchangedGossips = driversAtSameStop.map(_.gossips).reduceLeft(_ ++ _)
        driversAtSameStop.map(driver => Driver(driver.route, exchangedGossips))
    }.toSeq
  }

  case class Gossip(id: Int)

  case class Driver(route: BusRoute, gossips: Set[Gossip])
}
