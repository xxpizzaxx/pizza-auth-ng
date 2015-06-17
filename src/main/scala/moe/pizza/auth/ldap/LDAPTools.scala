package moe.pizza.auth.ldap

import com.unboundid.util.ssl._
import com.unboundid.ldap.sdk._
import moe.pizza.auth.AuthConfig
import moe.pizza.auth.models.User

import scala.collection.JavaConversions._
import scala.util.Try

/**
 * Created by Andi on 05/06/2015.
 */
class LDAPTools(config: AuthConfig) {
  val ssl = new SSLUtil()
  val socketFactory = ssl.createSSLServerSocketFactory()

  def checkCredentials(uid: String, passwd: String): Boolean = {
    val dn = "uid=%s".format(uid) + config.ldap.memberdn
    val br = new SimpleBindRequest(dn, passwd)
    br.setResponseTimeoutMillis(1000)
    val conn = new LDAPConnection(config.ldap.server, config.ldap.port)
    try {
      val result = conn.bind(br)
      if (result.getResultCode == ResultCode.SUCCESS) {
        conn.close();
        true
      } else {
        conn.close();
        false
      }
    } catch {
      case l: LDAPException => conn.close(); false
      case _ => conn.close(); false
    }
  }

  def loadUser(uid: String): Option[User] = {
    val br = new SimpleBindRequest(config.ldap.admin, config.ldap.password)
    br.setResponseTimeoutMillis(1000)
    val conn = new LDAPConnection(config.ldap.server, config.ldap.port)
    var result: Option[User] = None
    try {
      if (conn.bind(br).getResultCode == ResultCode.SUCCESS) {
        val filter = Filter.createEqualityFilter("uid", uid)
        val searchResult = conn.search(config.ldap.memberdn, SearchScope.SUB, filter)
        val r = searchResult.getSearchEntries.get(0).getAttributes.toSeq
        result = Some(User.fromLdap(r))
      }
    } finally {
      conn.close()
    }
    result
  }

  def loadUsers(uids: Seq[String]): Seq[Option[User]] = {
    val br = new SimpleBindRequest(config.ldap.admin, config.ldap.password)
    br.setResponseTimeoutMillis(1000)
    val conn = new LDAPConnection(config.ldap.server, config.ldap.port)
    var result: Seq[Option[User]] = Seq()
    try {
      if (conn.bind(br).getResultCode == ResultCode.SUCCESS) {
        result = uids.map { uid =>
          Try {
            val filter = Filter.createEqualityFilter("uid", uid)
            val searchResult = conn.search(config.ldap.memberdn, SearchScope.SUB, filter)
            val r = searchResult.getSearchEntries.get(0).getAttributes.toSeq
            User.fromLdap(r)
          }.toOption
        }
      }
    } finally {
      conn.close()
    }
    result
  }


}
