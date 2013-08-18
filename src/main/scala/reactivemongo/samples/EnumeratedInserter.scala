package reactivemongo.samples

import reactivemongo.samples.config._
import reactivemongo.api._
import reactivemongo.bson._
import play.api.libs.iteratee._
import com.typesafe.scalalogging.slf4j.Logging
import scala.concurrent.ExecutionContext.Implicits.global

object EnumeratedInserter extends MongoConfig with Logging {

  def main(args: Array[String]) {
    val collection = dbSamples("insertions")

    var _channel: Option[Concurrent.Channel[String]] = None

    val enumerator = Concurrent.unicast[String] { channel => _channel = Some(channel) }

    val toBSONDocument = Enumeratee.map[String] { item => BSONDocument("name" -> item) }

    collection.bulkInsert(enumerator &> toBSONDocument, 5)

    for( line <- io.Source.stdin.getLines ) {
      if(line != "quit") {
        logger.debug(s"Received $line")
        _channel.get.push(line)
      }
      else
        _channel.get.eofAndEnd
    }
  }

}