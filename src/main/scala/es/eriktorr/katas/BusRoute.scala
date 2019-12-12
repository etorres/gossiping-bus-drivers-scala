package es.eriktorr.katas

case class BusRoute(stops: Seq[Int]) {

  def stopAt(minute: Int): Int = stops(indexFrom(minute) % stops.size)

  private def indexFrom(minute: Int) = minute - 1
}
