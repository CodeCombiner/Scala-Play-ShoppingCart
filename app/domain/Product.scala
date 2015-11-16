package domain
import play.api.libs.json.Json

case class Product(
    val id: Int,
    val name: String,
    val catId: Int,
    val price: Double,
    val stock: Int
)

object JsonFormats {
  implicit val productFormat = Json.format[Product]
}