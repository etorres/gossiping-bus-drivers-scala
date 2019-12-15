package es.eriktorr.katas

class GossipsExchanger {

  def driversAfterExchangingGossipsAt(minute: Int, drivers: Drivers): Drivers = {
    drivers.groupBy(_.route.stopAt(minute)).flatMap {
      case (_, driversAtSameStop) =>
        val exchangedGossips = driversAtSameStop.map(_.gossips).reduceLeft(_ ++ _)
        driversAtSameStop.map(driver => Driver(driver.route, exchangedGossips))
    }.toSeq
  }
}
