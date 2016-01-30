package de.randombyte.nightmare_ai

import org.slf4j.{Marker, Logger}

package object config {
  implicit class ErrorLogger(logger: Logger) {
    /**
      * For better usability in my specific situation
      */
    def e(marker: Marker, msg: String): Option[Nothing] = {
      logger.error(marker, msg)
      None
    }
  }
}
