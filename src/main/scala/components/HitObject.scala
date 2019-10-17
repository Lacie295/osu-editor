package components

import utils.{Addition, Hitsound, Position, TimeStamp}

abstract class HitObject(p: Position, t: TimeStamp, hs: Hitsound = (0, 0)) extends Component(t) {
  private var _pos: Position = p
  //  base hitsound. saves sample set ((auto), normal, soft, drum) and sample index
  private var _hitsound: Hitsound = hs
  //  additions. saves an array of additions, one for each (whistle, finish, clap) and if active or not
  private var _additions: Array[Addition] = Array(new Addition(0, 0, false), new Addition(0, 0, false), new Addition(0, 0, false))// 1 place in the array for each addition type, if none they are 0

  def position: Position = _pos

  def position_=(set_pos: Position): Unit = _pos = set_pos

  def hitsound: Hitsound = _hitsound

  def hitsound_=(hs: Hitsound): Unit = _hitsound = hs

  def additions: Array[Addition] = _additions

  def additions_=(set: Array[Addition]): Unit = _additions = set

    //  edits addition at index (0-whistle, 1-finish, 2-clap) and sets it to the specified addition ad
  def setAddition(index: Int, ad: Addition): Unit = {
    index match {
      case 1 => _additions(0) = ad
      case 2 => _additions(1) = ad
      case 3 => _additions(2) = ad
    }
  }

    //  sets indexed addition to inactive(disabled). 0-whistle, 1-finish, 2-clap
  def removeAddition(index: Int): Unit = {
    _additions(index).active = false
  }

    //  sets all addtions to inactive
  def clearAdditions() : Unit= {
    for (x <- _additions) x.active = false
  }

  def overlaps(o: HitObject): Boolean = {
    o match {
      case o: HeldObject => o.overlaps(this)
      case _ => overlaps(o.timeStamp)
    }
  }

  def overlaps(t: TimeStamp): Boolean = {
    this.timeStamp == t
  }
}

object HitObject {
  implicit def objectToPosition(o: HitObject): Position = o.position
}
