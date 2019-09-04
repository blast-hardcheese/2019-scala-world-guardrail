package se.hardchee

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl._
import swagger.definitions._
import swagger.examples.user.{ UserHandler, UserResource }

object Foo extends App {
  new UserHandler {
    def createUser(respond: UserResource.createUserResponse.type
      )(body: Option[User]
      ): Future[UserResource.createUserResponse] = {
        Future.successful(respond.OK)
    }

    def getUser(respond: UserResource.getUserResponse.type
      )(id: Long
      ): Future[UserResource.getUserResponse] = ???
  }
}
