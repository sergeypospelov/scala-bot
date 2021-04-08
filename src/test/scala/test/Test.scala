package test

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

class Test extends AnyFunSuite with Matchers {
  test("useless") {
    assert(1 == 1)
  }
}
