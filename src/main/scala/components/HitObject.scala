package components

import utils.{Position, TimeStamp}

abstract class HitObject (p : Position, t: TimeStamp) extends Component(t) {
  private var pos: Position = p
}
