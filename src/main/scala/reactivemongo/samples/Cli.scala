package reactivemongo.samples

import playcli._
import play.api.libs.iteratee._

object Cli {

  def main(args: Array[String]) {
    val file = args(0)
    val pattern = args(1)

    val tail = CLI.enumerate(Seq("tail", "-f", file))
    val cat = CLI.enumerate(Seq("cat", file))
    val grep = CLI.pipe(Seq("grep", "--line-buffered", pattern))
    val printlnIteratee = Iteratee.foreach[Array[Byte]](s => print(new String(s)))

    tail &> grep |>>> printlnIteratee
  }

}