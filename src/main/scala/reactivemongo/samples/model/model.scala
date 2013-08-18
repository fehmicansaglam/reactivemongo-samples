package reactivemongo.samples.model

import org.joda.time.DateTime

case class SampleModel(
  id: String,
  name: String,
  price: Double,
  created: DateTime,
  updated: Option[DateTime])
