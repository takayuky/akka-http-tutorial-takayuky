package io.github.takayuky.api.repository

import cats.syntax.option._
import io.github.takayuky.entity.Item
import scala.concurrent.Future

trait ItemRepository {
  def fetch(itemId: Long): Future[Option[Item]] = {
    if (itemId % 2 == 0) Future.successful(Item(id = itemId, name = s"Item${itemId}").some)
    else                 Future.successful(none)
  }
}
