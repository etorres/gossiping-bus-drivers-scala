package es.eriktorr.katas

import org.scalatest.matchers.{MatchResult, Matcher}

class GossipsExchangerSpec extends UnitSpec with DriverMatchers {

  "Gossips exchanger" should "merge all gossips at current minute" in new GossipsExchanger {
    driversAfterExchangingGossipsAt(3, drivers) should knowAll(Set(Gossip1, Gossip2))
  }

  private lazy val BusRoute1: BusRoute = BusRoute(Seq(1, 2, 3))
  private lazy val BusRoute2: BusRoute = BusRoute(Seq(5, 4, 3))

  private lazy val Gossip1: Gossip = Gossip(1)
  private lazy val Gossip2: Gossip = Gossip(2)

  private lazy val drivers = Seq(Driver(BusRoute1, Set(Gossip1)), Driver(BusRoute2, Set(Gossip2)))
}

trait DriverMatchers {
  class DriverKnowsGossipsMatcher(gossips: Set[Gossip]) extends Matcher[Seq[Driver]] {
    override def apply(left: Seq[Driver]): MatchResult = MatchResult(
      matches = left.map(_.gossips).forall(_ == gossips),
      rawFailureMessage = s"not all drivers $left know all gossips $gossips",
      rawNegatedFailureMessage = "all drivers know all gossips")
  }

  def knowAll(gossips: Set[Gossip]) = new DriverKnowsGossipsMatcher(gossips)
}
