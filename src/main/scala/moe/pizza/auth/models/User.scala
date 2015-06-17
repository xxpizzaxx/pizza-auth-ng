package moe.pizza.auth.models

import java.util

import com.unboundid.ldap.sdk.Attribute

/**
 * Created by Andi on 14/03/2015.
 */
case class User (
                uid: String,
                characterName: String,
                corporation: String,
                ts3uid: Seq[String],
                keyID: Long,
                vCode: String,
                email: String,
                alliance: String,
                authGroup: Seq[String],
                accountStatus: String
                  )

object User {
  def fromLdap(r: Seq[Attribute]): User = {
    val map = r.map{attr => (attr.getName, attr.getValues)}.toMap
    new User(
      map.get("uid").get.head,
      map.get("characterName").get.head,
      map.get("corporation").get.head,
      map.get("ts3uid").get,
      map.get("keyID").get.head.toLong,
      map.get("vCode").get.head,
      map.get("email").get.head,
      map.get("alliance").get.head,
      map.get("authGroup").get,
      map.get("accountStatus").get.head
    )
  }
}
