package Components

abstract class HeldObject(p: Position, ep: Position, t: Int, et: Int) extends HitObject(p, t) {
  private var endTime: Int = et
  private var endPos: Position = ep
}
