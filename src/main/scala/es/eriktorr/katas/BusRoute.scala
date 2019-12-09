package es.eriktorr.katas

case class BusRoute(stops: Seq[Int]) {

  def stopAt(minute: Int): Int = {
    stops((minute - 1) % stops.size)
  }

}
