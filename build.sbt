name := "numb"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.4" % "test"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"
libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.7"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"

// Taking newer scala-xml and scala-reflect due to transient conflict.
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
//libraryDependencies += "org.scala-lang" %% "scala-reflect" % "2.11.8"

resolvers += Resolver.sonatypeRepo("public")
scalacOptions in Test += "-Yrangepos"