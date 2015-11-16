package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import repositories._
import domain._
import scala.xml.Null
import java.util.concurrent.TimeoutException
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import com.sun.tracing.Probe

class CartController extends Controller {

  implicit val cartWrites = Json.writes[Cart]
  implicit val cartReads = Json.reads[Cart]
  implicit val productWrites = Json.writes[Product]
  implicit val productReads = Json.reads[Product]

  def listCarts = Action {
    Ok(Json.toJson(CartRepositoryImpl.listCarts))
  }

  def createCart = Action(parse.json) { request =>
    unmarshalCart(request, (resource: Cart) => {
      val cart = Cart(0,
        resource.productId)
      CartRepositoryImpl.insertCart(cart)
      Created
    })
  }

  def findCart(id: Int) = Action {
    val cart = CartRepositoryImpl.findById(id)
    Ok(Json.toJson(cart))
  }

  def listProductInCarts = Action.async { implicit request =>
    ProductRepositoryImpl.listCartProducts(CartRepositoryImpl.listCarts).map(product => Ok(Json.toJson(product))).recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in cart")
        BadRequest(ex.getMessage)
    }
  }

  def deleteCart(id: Int) = Action(parse.json) { implicit request =>
    CartRepositoryImpl.deleteCart(id)
    NoContent
  }

  def updateCart(id: Int) = Action(parse.json) { implicit request =>
    unmarshalCart(request, (resource: Cart) => {
      CartRepositoryImpl.updateCart(resource)
      NoContent
    })
  }

  private def unmarshalCart(request: Request[JsValue], block: (Cart) => Result): Result = {
    request.body.validate[Cart].fold(
      valid = block,
      invalid = (e => {
        val error = e.mkString
        Logger.error(error)
        BadRequest(error)
      }))
  }
}