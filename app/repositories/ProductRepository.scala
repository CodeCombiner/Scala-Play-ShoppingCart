package repositories

import domain._
import persistence._
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import play.api.Play.current
import javax.inject.Inject
import com.redis._
import scala.concurrent.{ Future, Await }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.backend.DatabasePublisher
import slick.driver.MySQLDriver.api._
import play.api.db.DB

trait ProductRepository {

  def listProducts: Future[Seq[Product]]
  
  def listCartProducts(listCart: List[Cart]): Future[Seq[Product]]

  def insertProduct(product: Product): Future[Int]

  def updateProduct(id: Int, product: Product): Future[Int]

  def findById(id: Int): Future[Product]

  def deleteProduct(id: Int): Future[Int]

  def count: Future[Int]
  
}

object ProductRepositoryImpl extends ProductRepository {

  private val products = TableQuery[Products]

  private def db: Database = Database.forDataSource(DB.getDataSource())

  private def filterQuery(id: Int): Query[Products, Product, Seq] =
    products.filter(_.id === id)

  override def findById(id: Int): Future[Product] =
    try db.run(filterQuery(id).result.head)
    finally db.close

  override def count: Future[Int] =
    try db.run(products.length.result)
    finally db.close

  override def insertProduct(product: Product): Future[Int] =
    try db.run(products += product)
    finally db.close

  override def updateProduct(id: Int, product: Product): Future[Int] =
    try db.run(filterQuery(id).update(product))
    finally db.close

  override def deleteProduct(id: Int): Future[Int] =
    try db.run(filterQuery(id).delete)
    finally db.close

  override def listProducts(): Future[Seq[Product]] = {
    try {
      val query =
        (for {
          product <- products if product.name != null
        } yield (product))

      db.run(query.result)

    } finally { db.close() }
  }
  
  override def listCartProducts(listCart: List[Cart]): Future[Seq[Product]] = {
    val cartProductIds = List[Int]()
    
    listCart.foreach { name =>  cartProductIds :+ name.productId }

    try {
      val query =
        (for {
          product <- products 
          if product.id inSetBind cartProductIds
          
        } yield (product))

      db.run(query.result)

    } finally { db.close() }
  }
}
    
