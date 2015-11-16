package controllers

import play.api._
import play.api.mvc._
//import services.ProductServiceComponent
import domain._
import repositories._
import java.util.concurrent.TimeoutException
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._

class Application  extends Controller {
      
  implicit val authorWrites = Json.writes[Product]
  implicit val authorReads = Json.reads[Product]
    
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def findProduct(id: Int): Action[AnyContent] = Action.async { implicit request =>
    ProductRepositoryImpl.findById(id).map(product => Ok(Json.toJson(product))).recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in employee find process")
        InternalServerError(ex.getMessage)
    }
  }

}

