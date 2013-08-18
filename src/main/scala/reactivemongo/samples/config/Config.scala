package reactivemongo.samples.config

trait Config {
  import com.typesafe.config.ConfigFactory

  protected val config = ConfigFactory.load().getConfig("reactivemongo-samples")
}

object Config extends Config
