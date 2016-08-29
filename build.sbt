name := "numb"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.4" % "test"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"
libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.7"

// Taking newer scala-xml due to transient conflict between specs2 and scopt.
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.5"

resolvers += Resolver.sonatypeRepo("public")
scalacOptions in Test += "-Yrangepos"