package taska.config

import com.typesafe.config.Config
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location
import org.springframework.context.annotation.{Bean, Configuration}

import java.net.URI

@Configuration
class FlywayConfig {

  val MigrationRoot = "classpath:/db/migration"

  def getLocation(path: String): Location = {
    new Location(s"$MigrationRoot/$path")
  }

  def getDbVendor(dbUrl: String): String = {
    val dbUri = new URI(dbUrl)
    val vendorLength = dbUri.getSchemeSpecificPart.indexOf(":")
    dbUri.getSchemeSpecificPart.substring(0, vendorLength)
  }

  @Bean
  def flyway(cfg: Config): Flyway = {
    val dbConfig = cfg.getConfig("db")
    val dbUrl = dbConfig.getString("url")
    val dbUser = dbConfig.getString("user")
    val dbPassword = dbConfig.getString("password")
    val dbVendor = getDbVendor(dbUrl)

    val locations =
      Seq(
        getLocation("common"),
        getLocation(dbVendor)
      )

    Flyway
      .configure()
      .dataSource(dbUrl, dbUser, dbPassword)
      .locations(locations: _*)
      .load()
  }
}
