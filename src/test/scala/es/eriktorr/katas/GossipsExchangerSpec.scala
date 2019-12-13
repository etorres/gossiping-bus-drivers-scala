package es.eriktorr.katas

class GossipsExchangerSpec extends UnitSpec {

  private val BusRoute1: BusRoute = BusRoute(Seq(1, 2, 3))
  private val BusRoute2: BusRoute = BusRoute(Seq(5, 4, 3))

  private val Gossip1: Gossip = Gossip(1)
  private val Gossip2: Gossip = Gossip(2)

  "Gossips exchanger" should "merge gossips from all drivers at a given minute" in new GossipsExchanger {
    val drivers = Seq(
      Driver(BusRoute1, Set(Gossip1)),
      Driver(BusRoute2, Set(Gossip2)))

    driversAfterExchangingGossipsAt(3, drivers) shouldBe Seq(
      Driver(BusRoute1, Set(Gossip1, Gossip2)),
      Driver(BusRoute2, Set(Gossip1, Gossip2)))
  }
}
