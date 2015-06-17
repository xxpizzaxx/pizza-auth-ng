package moe.pizza.auth

import org.scalatra._
import org.scalatra.scalate.ScalateSupport

class AuthServlet extends ScalatraServlet with ScalateSupport {

  get("/") {
    <html>
      <h1>Hello, world!</h1>
      <a href="woof">Try an SSP Page</a>
    </html>
  }

  get("/woof") {
    contentType="text/html"
    ssp("frontpage.ssp")
  }

  case class Flower(slug: String, name: String) {
    def toXML= <flower name={name}>{slug}</flower>
  }

  val all = List(
    Flower("yellow-tulip", "Yellow Tulip"),
    Flower("red-rose", "Red & Rose"),
    Flower("black-rose", "Black Rose"))

  get("/flowers"){
    contentType="text/xml"
    <flowers>
      { all.map(_.toXML) }
    </flowers>
  }

}