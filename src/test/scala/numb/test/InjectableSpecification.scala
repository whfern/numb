package numb.test

import numb.app.NumbModule
import org.specs2.mutable.Specification
import scaldi.{Injectable, Injector}

/**
  * Configuration for specification dependency injection that allows us to utilize the bindings present in
  * our {@link NumbModule} with test overrides present in {@link TestModule}
  *
  * @author whfern
  */
class InjectableSpecification(implicit inj: Injector) extends Specification with Injectable {
  val injector = new TestModule :: new NumbModule
}
