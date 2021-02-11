package taska.config

import akka.actor.typed.{ActorSystem, DispatcherSelector}
import com.typesafe.config.Config
import org.springframework.context.annotation.{Bean, Configuration}

import java.net.URI
import scala.concurrent.ExecutionContextExecutor

case class DbProps(
    url: String,
    user: String,
    password: String,
    vendor: String
)

@Configuration
class DbConfig {
  def getDbVendor(dbUrl: String): String = {
    val dbUri = new URI(dbUrl)
    val vendorLength = dbUri.getSchemeSpecificPart.indexOf(":")
    dbUri.getSchemeSpecificPart.substring(0, vendorLength)
  }

  @Bean
  def dbProps(cfg: Config): DbProps = {
    val dbConfig = cfg.getConfig("db")
    val dbUrl = dbConfig.getString("url")
    DbProps(
      dbUrl,
      dbConfig.getString("user"),
      dbConfig.getString("password"),
      getDbVendor(dbUrl)
    )
  }

  @Bean
  def dbDispatcher(
      system: ActorSystem[Nothing]
  ): ExecutionContextExecutor = {
    system.dispatchers.lookup(
      DispatcherSelector.fromConfig("dispatchers.db")
    )
  }
}
