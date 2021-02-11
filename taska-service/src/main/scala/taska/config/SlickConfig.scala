package taska.config

import com.typesafe.config.Config
import org.springframework.context.annotation.{Bean, Configuration}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

@Configuration
class SlickConfig {

  @Bean
  def databaseConfig(config: Config): DatabaseConfig[JdbcProfile] = {
    DatabaseConfig.forConfig("slick", config)
  }
}
