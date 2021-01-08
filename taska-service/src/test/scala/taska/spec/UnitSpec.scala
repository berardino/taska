package taska.spec

import akka.actor.testkit.typed.scaladsl.LogCapturing
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike
import taska.gen.SynthLike

trait UnitSpec
    extends AnyWordSpecLike
    with BeforeAndAfterEach
    with LogCapturing
    with SynthLike
