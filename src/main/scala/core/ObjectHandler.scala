package core

import components.{Circle, Inherited_legacy, Slider, Spinner, TimingPoint, Uninherited_legacy}
import utils.{Position, TimeStamp}

object ObjectHandler {
  def circle(p: Position, t: TimeStamp): Circle = new Circle(p, t)

  def slider(p: Position, t: TimeStamp, et: TimeStamp, r: Int = 0): Slider = new Slider(p, t, et, r)

  def spinner(t: TimeStamp, et: TimeStamp): Spinner = new Spinner(t, et)

  def timingPoint(t: TimeStamp, BPM: Double): TimingPoint = new TimingPoint(t, BPM)

  def inherited(t: TimeStamp, multi: Double, ss: Int, si: Int, vol: Int, ki: Boolean): Inherited_legacy = new Inherited_legacy(t, multi, ss, si, vol, ki)

  def uninherited(t: TimeStamp, BPM: Double, m: Int, ss: Int, si: Int, vol: Int, ki: Boolean): Uninherited_legacy = new Uninherited_legacy(t, BPM, m, ss, si, vol, ki)
}
