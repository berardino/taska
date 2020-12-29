package taska.config

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.stream.Materializer
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class AkkaConfig {

  @Bean
  def config(): Config = {
    ConfigFactory.load()
  }

  @Bean
  def actorSystem(config: Config): ActorSystem[Nothing] = {
    ActorSystem[Nothing](Behaviors.empty, "TaskaSystem", config)
  }

  @Bean
  def clusterSharding(actorSystem: ActorSystem[Nothing]): ClusterSharding = {
    ClusterSharding(actorSystem)
  }

  @Bean
  def materializer(system: ActorSystem[Nothing]): Materializer = {
    Materializer(system)
  }

}
