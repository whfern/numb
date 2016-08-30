package numb.test

import com.typesafe.config.ConfigFactory
import numb.app.NumbModule
import org.specs2.mutable.Specification
import scaldi.{Injectable, Injector, TypesafeConfigInjector}

/**
  * Configuration for specification dependency injection that allows us to utilize the bindings present in
  * our {@link NumbModule} with test overrides present in {@link TestModule}
  *
  * @author whfern
  */
class InjectableSpecification extends Specification with Injectable {
  implicit val injector = new TestModule :: TypesafeConfigInjector(ConfigFactory.load()) :: new NumbModule
}
