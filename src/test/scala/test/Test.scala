package test

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import scalaBot.parser.ParserCalendarItems.{toAssignmentItems, toClassItems}
import scalaBot.parser.ParserICS

class Test extends AnyFunSuite with Matchers {
  test("useless") {
    assert(1 == 1)
  }

//  test("parse") {
//    var events = ParserICS.parse("assignments.ics")
//    println(toAssignmentItems(events).map { _.getEvents })
//    events = ParserICS.parse("classes.ics")
//    println(toClassItems(events).map { _.getEvents })
//  }
}
