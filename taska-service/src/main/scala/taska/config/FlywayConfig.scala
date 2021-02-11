package taska.config

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class FlywayConfig {

  val MigrationRoot = "classpath:/db/migration"

  def getLocation(path: String): Location = {
    new Location(s"$MigrationRoot/$path")
  }

  @Bean
  def flyway(dbProps: DbProps): Flyway = {

    val locations =
      Seq(
        getLocation("common"),
        getLocation(dbProps.vendor)
      )

    Flyway
      .configure()
      .dataSource(dbProps.url, dbProps.user, dbProps.password)
      .locations(locations: _*)
      .load()
  }
}
