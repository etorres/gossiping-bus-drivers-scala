package es.eriktorr.katas

class GossipsBroadcasterSpec extends UnitSpec {

  "Gossips broadcaster" should "find the stops it takes to spread all gossips to all drivers" in new GossipsBroadcaster {
    stopsToSpreadAllGossips(Seq(
      BusRoute(Seq(3, 1, 2, 3)),
      BusRoute(Seq(3, 2, 3, 1)),
      BusRoute(Seq(4, 2, 3, 4, 5))
    )) shouldBe "5"
  }

  "Gossips broadcaster" should "answer never when gossips are not spread by the end of the day" in new GossipsBroadcaster {
    stopsToSpreadAllGossips(Seq(
      BusRoute(Seq(2, 1, 2)),
      BusRoute(Seq(5, 2, 8))
    )) shouldBe "never"
  }
}
