package components

import utils.{Hitsound, Position, TimeStamp}

/**
 * The basic hitobject class
 * @param p: its positon on screen
 * @param t: the component's timestamp
 * @param hs: its associated hitsound
 */
abstract class HitObject(p: Position, t: TimeStamp, hs: Hitsound = (0, 0)) extends Component(t) {
  private var _pos: Position = p
  //  base hitsound. saves sample set ((auto), normal, soft, drum) and sample index
  private var _hitsound: Hitsound = hs
  //  additions. saves an array of additions, one for each (whistle, finish, clap) and if active or not
  private var _additions: Array[Boolean] = Array(false, false, false) // 1 place in the array for each addition type, if none they are 0
  private var _additionsHitsound: Hitsound = new Hitsound(0, 0) // 1 place in the array for each addition type, if none they are 0

  // getters and setters
  def position: Position = _pos

  def position_=(set_pos: Position): Unit = _pos = set_pos

  def hitsound: Hitsound = _hitsound

  def hitsound_=(hs: Hitsound): Unit = _hitsound = hs

  def additions: Array[Boolean] = _additions

  def additions_=(set: Array[Boolean]): Unit = _additions = set

  def additionsHitsound: Hitsound = _additionsHitsound

  def additionsHitsound_=(hs: Hitsound): Unit = _additionsHitsound = hs

  // edits addition at index (0-whistle, 1-finish, 2-clap) and sets it to the specified addition ad
  def setAddition(index: Int): Unit = {
    _additions(index - 1) = true
  }

  // sets indexed addition to inactive(disabled). 0-whistle, 1-finish, 2-clap
  def unSetAddition(index: Int): Unit = {
    _additions(index - 1) = false
  }

  // sets all addtions to inactive
  def clearAdditions() : Unit= {
    _additions.foreach(_ = false)
  }
}

/**
 * convert to a position if needed
 */
object HitObject {
  implicit def objectToPosition(o: HitObject): Position = o.position
}
