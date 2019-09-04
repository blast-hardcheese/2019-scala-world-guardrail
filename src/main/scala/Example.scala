package se.hardchee

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl._
import cats.data._
import cats.implicits._
import swagger.definitions._
import swagger.examples.user.{ UserClient, UserHandler, UserResource }

object Foo extends App {
  implicit val as = akka.actor.ActorSystem()
  implicit val mat = akka.stream.ActorMaterializer()

  val handler = new UserHandler {
    def createUser(respond: UserResource.createUserResponse.type
      )(body: Option[User]
      ): Future[UserResource.createUserResponse] = {
        Future.successful(body.fold(respond.Forbidden)(respond.OK))
    }

    def getUser(respond: UserResource.getUserResponse.type
      )(id: Long
      ): Future[UserResource.getUserResponse] = ???
  }

  val route = UserResource.routes(handler)
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

	val client = UserClient.httpClient(Route.asyncHandler(route))

  case class ErrorModel(message: String)

  val handleAkkaCrap: Either[Throwable, HttpResponse] => ErrorModel =
    _.fold(t => ErrorModel(t.toString()), r => ErrorModel(r.toString()))

  val x = for {
    userResp <- client.createUser().leftMap(handleAkkaCrap)
  } yield {
    userResp.fold(
      handleOK=_.fullName,
      handleForbidden="Couldn't get the user's name"
    )
  }

  val y = Await.result(x.value, 60.seconds)
  println(y)

  as.terminate()

//  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
//  scala.io.StdIn.readLine() // let it run until user presses return
//  bindingFuture
//    .flatMap(_.unbind()) // trigger unbinding from the port
//    .onComplete(_ => as.terminate()) // and shutdown when done
}
