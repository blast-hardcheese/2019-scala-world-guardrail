package se.hardchee

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl._
import swagger.definitions._
import swagger.examples.user.{ UserHandler, UserResource }

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

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  scala.io.StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => as.terminate()) // and shutdown when done
}
