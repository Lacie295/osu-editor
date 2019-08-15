package components

import utils.TimeStamp

abstract class AbstractTimingPoint(t: TimeStamp) extends Component(t) {

}

object AbstractTimingPoint {
  implicit def abstractTimingPointToTimeStamp(a: AbstractTimingPoint): TimeStamp = a.timeStamp
}
