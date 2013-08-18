package reactivemongo.samples.model

package object model {
  import reactivemongo.bson._
  import com.github.nscala_time.time.Imports._

  implicit object DatetimeReader extends BSONReader[BSONDateTime, DateTime] {
    def read(bson: BSONDateTime): DateTime = new DateTime(bson.value)
  }

  implicit object DatetimeWriter extends BSONWriter[DateTime, BSONDateTime] {
    def write(t: DateTime): BSONDateTime = BSONDateTime(t.getMillis)
  }

  implicit val sampleModelFormat = Macros.handler[SampleModel]
}