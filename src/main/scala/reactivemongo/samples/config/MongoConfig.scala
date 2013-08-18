package reactivemongo.samples.config

trait MongoConfig extends Config {
  import com.typesafe.config.ConfigFactory
  import scala.collection.JavaConversions._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  import reactivemongo.api._
  import reactivemongo.api.collections.default._
  import reactivemongo.core.commands._
  import reactivemongo.bson._
  import play.api.libs.iteratee._

  val hosts: Seq[String] = asScalaBuffer(config.getStringList("db.hosts")).toSeq
  val dbNameSamples: String = config.getString("db.samples.name")

  lazy val driver = new MongoDriver
  lazy val connection = driver.connection(hosts)

  // Gets a reference to the database
  lazy val dbSamples = connection(dbNameSamples)

  def count(collection: BSONCollection): Future[Int] = collection.db command Count(collection.name)

  class BSONCollectionDecorator(collection: BSONCollection) {
    def ?>>(query: BSONDocument): Enumerator[BSONDocument] = collection.find(query).cursor[BSONDocument].enumerate
    def >>[A](i: Iteratee[BSONDocument, A]): Future[Iteratee[BSONDocument, A]] = collection.find(BSONDocument()).cursor[BSONDocument].enumerate.apply(i)
  }

  implicit def collectionToDecorator(collection: BSONCollection): BSONCollectionDecorator = new BSONCollectionDecorator(collection)
}

object MongoConfig extends MongoConfig
