package moe.pizza.auth.ldap

import moe.pizza.auth.AuthConfig
import moe.pizza.eveapi.{ApiKey, EVEAPI}


class KeyTools(config: AuthConfig) {

  def BlueAlliances() = {
    val key = new Some(ApiKey(config.auth.standingsKey.keyID, config.auth.standingsKey.vCode))
    val api = new EVEAPI()(key)
  }

  lazy val blueAlliances = BlueAlliances()


}
