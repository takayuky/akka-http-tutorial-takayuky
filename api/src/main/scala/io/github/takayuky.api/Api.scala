package io.github.takayuky.api

import akka.Done
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.github.takayuky.api.repository.{ItemRepository, OrderRepository}
import io.github.takayuky.entity.{Item, Order}
import scala.concurrent.Future
import spray.json.DefaultJsonProtocol._

class Api {
  private val itemRepository = new ItemRepository {}
  private val orderRepository = new OrderRepository {}

  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)

  val route: Route =
    get {
    pathPrefix("item" / LongNumber) { id =>
      val maybeItem: Future[Option[Item]] = itemRepository.fetch(id)

      onSuccess(maybeItem) {
        case Some(item) => complete(item)
        case None       => complete(StatusCodes.NotFound)
      }
    }
  } ~
    post {
      path("create-order") {
        entity(as[Order]) { order =>
          val saved: Future[Done] = orderRepository.save(order)
          onComplete(saved) { _ =>
            complete("order created")
          }
        }
      }
    }
}

object Api {
  def apply(): Api = new Api
}
