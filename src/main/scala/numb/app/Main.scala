package numb.app

import com.typesafe.config.{Config, ConfigFactory}
import scaldi.{Injector, TypesafeConfigInjector}

/**
  * Main object the holds the main function for the Numb application. It parses command line arguments and configures
  * the translator and its bindings.
  *
  * @author whfern
  */
object Main extends App {

  // Declare injector bindings where properties are added after module bindings
  implicit val injector : Injector = TypesafeConfigInjector(ConfigFactory.load()) :: new NumbModule


}
