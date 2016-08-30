package numb.app

import java.lang.IllegalArgumentException

import com.typesafe.config.{Config, ConfigFactory}
import numb.translate.NumberTranslationService
import scaldi.{Injectable, Injector, TypesafeConfigInjector}
import scopt.OptionParser

/**
  * Main object the holds the main function for the Numb application. It parses command line arguments and configures
  * the translator and its bindings.
  *
  * @author whfern
  */
object Main extends App with Injectable {
  // Declare injector bindings where properties are added after module bindings
  implicit val injector : Injector = TypesafeConfigInjector(ConfigFactory.load()) :: new NumbModule
  val translator: NumberTranslationService = inject[NumberTranslationService]
  if (args.length == 1) {
    try {
      println(translator.translate(args(0)))
    } catch {
      case e:IllegalArgumentException => println("Please enter a number only containing digits between 0 and 1000001")
    }
  } else {
    println("Please pass exactly one argument only containing digits.")
  }
}

