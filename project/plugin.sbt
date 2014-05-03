
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.5.1")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

scalacOptions ++= Seq("-deprecation", "-feature")
