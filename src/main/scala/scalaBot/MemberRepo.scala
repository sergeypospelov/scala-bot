package scalaBot

class Member

trait MemberRepo {
  def addMember(t: Member): Unit

  def deleteMember(t: Member): Unit

  def findMemberById(ind: Int): Member

  def toList: List[Member]
}
