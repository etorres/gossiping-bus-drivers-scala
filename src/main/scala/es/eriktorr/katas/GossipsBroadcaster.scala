package es.eriktorr.katas

import es.eriktorr.katas.BusRoute.{isBeforeTheEndOfTheDay, isAfterTheEndOfTheDay}

import scala.annotation.tailrec

class GossipsBroadcaster extends GossipsExchanger {

  def stopsToSpreadAllGossips(busRoutes: BusRoutes): String = {
    val drivers = driversFrom(busRoutes)
    val stopCount = stopsToSpreadGossipsToAll(drivers)
    if (isBeforeTheEndOfTheDay(stopCount)) stopCount.toString else "never"
  }

  private def stopsToSpreadGossipsToAll(drivers: Drivers): Int = {
    @tailrec
    def stopsToSpreadGossipsToAll(drivers: Drivers, minute: Int): Int = if (isAfterTheEndOfTheDay(minute)) minute else {
      val updatedDrivers = driversAfterExchangingGossipsAtCurrentStop(drivers)
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
