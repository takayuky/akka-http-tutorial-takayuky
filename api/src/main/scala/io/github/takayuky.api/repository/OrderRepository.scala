package io.github.takayuky.api.repository

import akka.Done
import io.github.takayuky.entity.Order
import scala.concurrent.Future

trait OrderRepository {
  def save(order: Order): Future[Done] = Future.successful(Done)
}
