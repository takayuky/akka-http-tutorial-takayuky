import sbt.Keys.scalaVersion

lazy val core = project.in(file("core"))
  .settings(commonSettings)
  .settings(coreSettings)

lazy val api = project.in(file("api"))
  .dependsOn(core % "compile->compile; test->test")
  .settings(commonSettings)
  .settings(apiSettings)

lazy val commonSettings = Seq(
  scalaVersion := "2.12.2",

  scalacOptions ++= Seq(
    "-deprecation",         // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",   // Specify character encoding used by source files.
    "-explaintypes",        // Explain type errors in more detail.
    "-feature",             // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked",           // Enable additional warnings where generated code depends on assumptions.
    "-Xfatal-warnings",     // Fail the compilation if there are any warnings.
    "-Xfuture",             // Turn on future language features.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    "-Yno-adapted-args"     // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  ),
  incOptions := incOptions.value.withNameHashing(true),
  javaOptions in Test += "-Dconfig.resource=test.conf",
  fork in Test := true,

  resolvers += Resolver.sonatypeRepo("releases")
)

//
// core
//

lazy val coreSettings = coreLibraryDependencies ++ coreTestingLibraryDependencies

lazy val coreLibraryDependencies = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats"          % "0.9.0",
    "io.circe"      %% "circe-core"    % "0.8.0",
    "io.circe"      %% "circe-generic" % "0.8.0",
    "io.circe"      %% "circe-parser"  % "0.8.0",
    "org.slf4j"     %  "slf4j-nop"     % "1.7.25"
  )
)

lazy val coreTestingLibraryDependencies = Seq(
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck"                  % "1.13.4" % "test",
    "org.scalatest"  %% "scalatest"                   % "3.0.1"  % "test",
    "org.scalamock"  %% "scalamock-scalatest-support" % "3.6.0"  % "test",
    "org.mockito"     % "mockito-core"                % "2.8.47" % "test"
  )
)

//
// api
//

lazy val apiSettings = apiMainClassSettings ++ apiLibraryDependencies

lazy val apiMainClassSettings = Seq(
  mainClass in Compile := Some("net.nend.api.Server")
)

lazy val apiLibraryDependencies = Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http"       % "10.0.8",
    "de.heikoseeberger" %% "akka-http-circe" % "1.16.1"
  )
)
