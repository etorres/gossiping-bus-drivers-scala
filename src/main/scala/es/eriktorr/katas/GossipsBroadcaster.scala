package es.eriktorr.katas

import scala.annotation.tailrec

class GossipsBroadcaster()(implicit val gossipsExchanger: GossipsExchanger) {

  def stopsToSpreadAllGossips(busRoutes: Seq[BusRoute]): String = {
    val drivers = driversFrom(busRoutes)
    val stops = stopsToSpreadAllGossips(minute = 1, drivers)
    if (stops <= BusRoute.MaxStops) stops.toString else "never"
  }

  @tailrec
  private def stopsToSpreadAllGossips(minute: Int, drivers: Seq[Driver]): Int = if (minute > BusRoute.MaxStops) minute else {
    val updatedDrivers = gossipsExchanger.driversAfterExchangingGossipsAt(minute, drivers)
    if (haveAllTheGossips(updatedDrivers)) minute else stopsToSpreadAllGossips(minute + 1, updatedDrivers)
  }

  private def haveAllTheGossips(drivers: Seq[Driver]) = !drivers.exists(_.gossips.size != drivers.size)

  private def driversFrom(busRoutes: Seq[BusRoute]) = {
    busRoutes.zipWithIndex.map {
      case (busRoute, index) => Driver(busRoute, Set(Gossip(index)))
    }
  }
}
