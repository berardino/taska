package taska.gen

import org.scalacheck.Gen

import scala.util.Random

object Synth extends SynthLike

trait SynthLike {
  def genStr(): String = {
    Gen.alphaStr.sample.getOrElse(Random.nextString(10))
  }
  def genOptStr(): Option[String] = { Gen.alphaStr.sample }
}
