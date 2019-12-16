package es.eriktorr.katas

import scala.annotation.tailrec

class GossipsBroadcaster()(implicit val gossipsExchanger: GossipsExchanger) {

  def stopsToSpreadAllGossips(busRoutes: BusRoutes): String = {
    val drivers = driversFrom(busRoutes)
    val stops = stopsToSpreadGossipsToAll(drivers)
    if (happenedBeforeTheEndOfTheDay(stops)) stops.toString else "never"
  }

  private def happenedBeforeTheEndOfTheDay(stops: Int) = {
    stops <= BusRoute.MaxStops
  }

  private def stopsToSpreadGossipsToAll(drivers: Drivers): Int = {
    @tailrec
    def stopsToSpreadGossipsToAll(drivers: Drivers, minute: Int): Int = if (minute > BusRoute.MaxStops) minute else {
      val updatedDrivers = gossipsExchanger.driversAfterExchangingGossipsAt(minute, drivers)
      if (haveAllTheGossips(updatedDrivers)) minute else stopsToSpreadGossipsToAll(updatedDrivers, minute + 1)
    }
    stopsToSpreadGossipsToAll(drivers, minute = 1)
  }

  private def haveAllTheGossips(drivers: Drivers) = !drivers.exists(_.gossips.size != drivers.size)

  private def driversFrom(busRoutes: BusRoutes) = {
    busRoutes.zipWithIndex.map {
      case (busRoute, index) => Driver(busRoute, Set(Gossip(index)))
    }
  }
}
