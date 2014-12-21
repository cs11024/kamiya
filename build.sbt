name := "kamiya"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "jfree" % "jfreechart" % "1.0.13",
  "jfree" % "jcommon" % "1.0.16"
)

play.Project.playJavaSettings