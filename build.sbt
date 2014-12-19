name := "kamiya"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "jfree" % "jfreechart" % "1.0.9",
  "jfree" % "jcommon" % "1.0.19"
)

play.Project.playJavaSettings