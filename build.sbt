guardrailTasks in Compile := List(
  ScalaClient(file("petstore.yaml")),
  ScalajsClient(file("petstore.yaml")),
  ScalaServer(file("petstore.yaml"))
)

val akkaVersion       = "10.0.14"
val catsVersion       = "1.4.0"
val circeVersion      = "0.10.1"
val scalatestVersion  = "3.0.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"         % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
  "io.circe"          %% "circe-core"        % circeVersion,
  "io.circe"          %% "circe-generic"     % circeVersion,
  "io.circe"          %% "circe-java8"       % circeVersion,
  "io.circe"          %% "circe-parser"      % circeVersion,
  "org.scalatest"     %% "scalatest"         % scalatestVersion % Test,
  "org.typelevel"     %% "cats-core"         % catsVersion
)
