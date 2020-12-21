package taska.config

import com.typesafe.config.Config
import org.flywaydb.core.Flyway
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class FlywayConfig {

  @Bean
  def flyway(cfg: Config): Flyway = {
    val dbConfig = cfg.getConfig("db")
    val dbUrl = dbConfig.getString("url")
    val dbUser = dbConfig.getString("user")
    val dbPassword = dbConfig.getString("password")

    Flyway
      .configure()
      .dataSource(dbUrl, dbUser, dbPassword)
      .load()
  }
}
