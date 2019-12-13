package es.eriktorr.katas

import es.eriktorr.katas.BusRouteGenerator.{between3And20Stops, minutesOfADay}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

import scala.annotation.tailrec

object BusRouteSpec extends Properties("BusRouteSpec") {
  property("busRoute") = forAll(between3And20Stops, minutesOfADay) { (testCase, minute) =>
    val busRoute = BusRoute(testCase.stops)
    busRoute.stopAt(minute) == testCase.stopAt(minute)
  }
}

object BusRouteGenerator {
  private val MinutesOfADay = BusRoute.MaxStops

  val minutesOfADay: Gen[Int] = Gen.choose(1, MinutesOfADay)

  val between3And20Stops: Gen[AllDayBusRoute] = for {
    numberOfStops <- numberOfStopsBetween(3, 20)
    stops <- sequenceOfLength(numberOfStops)
  } yield AllDayBusRoute(stops, cyclic(stops).take(MinutesOfADay))

  def numberOfStopsBetween(minimum: Int, maximum: Int): Gen[Int] = Gen.choose(minimum, maximum)

  def sequenceOfLength(numberOfStops: Int): Gen[Seq[Int]] = Gen.listOfN(numberOfStops, Gen.choose(1, 100))

  @tailrec
  def cyclic(stops: Seq[Int]): Seq[Int] = if (stops.size >= MinutesOfADay) stops else cyclic(stops ++ stops)

  case class AllDayBusRoute(stops: Seq[Int], private val allDayStops: Seq[Int]) {
    def stopAt(minute: Int): Int = allDayStops(minute - 1)
  }
}
