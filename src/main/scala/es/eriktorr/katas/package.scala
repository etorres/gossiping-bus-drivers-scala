package es.eriktorr

package object katas {

  case class Gossip(id: Int)
  case class Driver(route: BusRoute, gossips: Gossips)

  type BusRoutes = Seq[BusRoute]
  type Gossips = Set[Gossip]
  type Drivers = Seq[Driver]

}
