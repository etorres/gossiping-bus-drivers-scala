package es.eriktorr.katas

import scala.annotation.tailrec

class GossipsBroadcaster()(implicit val gossipsExchanger: GossipsExchanger) {

  def stopsToSpreadAllGossips(busRoutes: Seq[BusRoute]): String = {
    val drivers = driversFrom(busRoutes)
    val stops = stopsToSpreadAllGossipsTo(drivers)
    if (stops <= BusRoute.MaxStops) stops.toString else "never"
  }

  private def stopsToSpreadAllGossipsTo(drivers: Seq[Driver]): Int = {
    @tailrec
    def stopsToSpreadAllGossipsTo(drivers: Seq[Driver], minute: Int): Int = if (minute > BusRoute.MaxStops) minute else {
      val updatedDrivers = gossipsExchanger.driversAfterExchangingGossipsAt(minute, drivers)
      if (haveAllTheGossips(updatedDrivers)) minute else stopsToSpreadAllGossipsTo(updatedDrivers, minute + 1)
    }
    stopsToSpreadAllGossipsTo(drivers, minute = 1)
  }

  private def haveAllTheGossips(drivers: Seq[Driver]) = !drivers.exists(_.gossips.size != drivers.size)

  private def driversFrom(busRoutes: Seq[BusRoute]) = {
    busRoutes.zipWithIndex.map {
      case (busRoute, index) => Driver(busRoute, Set(Gossip(index)))
    }
  }
}
