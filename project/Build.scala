import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object ReactiveMongoSamplesBuild extends Build {

  val mergeSettings = mergeStrategy in assembly <<= (mergeStrategy in assembly) {
    (old) => {
      case "rootdoc.txt" => MergeStrategy.first
      case x => old(x)
    }
  }

  lazy val reactiveMongoSamples = Project(
    id = "reactivemongo-samples",
    base = file("."),
    settings = Project.defaultSettings ++ assemblySettings ++ mergeSettings ++ Seq(
      name := "ReactiveMongo Samples",
      organization := "Fehmi Can Saglam",
      version := "0.10-SNAPSHOT",
      scalaVersion := "2.10.2",
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:postfixOps", "-language:implicitConversions"),
      libraryDependencies ++= Seq(
        "org.reactivemongo" %% "reactivemongo" % "0.10-SNAPSHOT",
        "fr.greweb" %% "playcli" % "0.1",
        "com.github.nscala-time" %% "nscala-time" % "0.4.2",
        "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"
      ),
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
      resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
    )
  )
}
