package test
import org.scalatest.funsuite.*

class Test extends FunSuite with Matchers {
  test("useless") {
    assert(1 == 1)
  }
}
