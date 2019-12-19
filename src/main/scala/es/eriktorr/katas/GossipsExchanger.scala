package es.eriktorr.katas

trait GossipsExchanger {

  def driversAfterExchangingGossipsAtCurrentStop(drivers: Drivers): Drivers = {
    drivers.groupBy(_.route.stops.head).flatMap {
      case (_, driversAtSameStop) =>
        val exchangedGossips = driversAtSameStop.map(_.gossips).reduceLeft(_ ++ _)
        driversAtSameStop.map(driver => {
          driver.copy(
            route = advanceToNextStop(driver.route),
            gossips = exchangedGossips
          )
        })
    }.toSeq
  }

  def advanceToNextStop(busRoute: BusRoute): BusRoute = {
    busRoute.copy(stops = busRoute.stops.tail :+ busRoute.stops.head)
  }
}
