package components

import utils.TimeStamp

/**
 * an abstract timing point, has to be implemented. Used for collections.
 * @param t: timestamp the timing point is set at
 */
abstract class AbstractTimingPoint(t: TimeStamp) extends Component(t) {

}
