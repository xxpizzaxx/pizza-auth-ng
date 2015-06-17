package moe.pizza.auth.ldap

/**
 * Created by Andi on 05/06/2015.
 */
object Main extends App {
  val l = new LDAPTools
  l.loadUser("lucia_denniard")
  println("done")
}
