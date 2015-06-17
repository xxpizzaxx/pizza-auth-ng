package moe.pizza.auth

/**
 * Created by Andi on 05/06/2015.
 */

case class LdapConfig(
                     server: String,
                     port: Int,
                     admin: String,
                     password: String,
                     basedn: String,
                     memberdn: String
                       )

case class ApiKey(keyID: Int, vCode: String)

case class CoreConfig(
                     standingsKey: ApiKey,
                     standingsThreshold: Double
                       )

case class AuthConfig(
  ldap: LdapConfig,
  auth: CoreConfig
)