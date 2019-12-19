package es.eriktorr.katas

import org.scalatest.matchers.{MatchResult, Matcher}

class GossipsExchangerSpec extends UnitSpec with DriverMatchers {

  "Gossips exchanger" should "merge all gossips at current minute" in new GossipsExchanger {
    driversAfterExchangingGossipsAtCurrentStop(drivers) should knowAll(Set(Gossip1, Gossip2))
  }

  "Gossips exchanger" should "advance route to the next stop" in new GossipsExchanger {
    advanceToNextStop(BusRoute2) shouldBe BusRoute(Seq(4, 5, 3))
  }

  private lazy val BusRoute1: BusRoute = BusRoute(Seq(3, 2))
  private lazy val BusRoute2: BusRoute = BusRoute(Seq(3, 4, 5))

  private lazy val Gossip1: Gossip = Gossip(1)
  private lazy val Gossip2: Gossip = Gossip(2)

  private lazy val drivers = Seq(Driver(BusRoute1, Set(Gossip1)), Driver(BusRoute2, Set(Gossip2)))
}

trait DriverMatchers {
  class DriverKnowsGossipsMatcher(gossips: Gossips) extends Matcher[Drivers] {
    override def apply(left: Drivers): MatchResult = MatchResult(
      matches = left.map(_.gossips).forall(_ == gossips),
      rawFailureMessage = s"not all drivers $left know all gossips $gossips",
      rawNegatedFailureMessage = "all drivers know all gossips")
  }

  def knowAll(gossips: Gossips) = new DriverKnowsGossipsMatcher(gossips)
}
