package scalaBot

trait UserRepo {
  def addUser(t: User): Unit

  def deleteUser(t: User): Unit

  def findUserById(ind: Int): User

  def toList: List[User]
}
