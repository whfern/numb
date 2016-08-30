package numb.app

import numb.translate.{DigitStringToNumberPartConverter, NumberPartToWordConverter, NumberTranslationService, PartBasedTranslationService}
import scaldi.Module

/**
  * Module that specifies the bindings for scaldi for building out the object graph.
  *
  * @author whfern
  */
class NumbModule extends Module {
  // Translation Service Components
  bind[NumberTranslationService] to injected[PartBasedTranslationService]
  bind[DigitStringToNumberPartConverter] to injected[DigitStringToNumberPartConverter]
  bind[NumberPartToWordConverter] to injected[NumberPartToWordConverter]
}
