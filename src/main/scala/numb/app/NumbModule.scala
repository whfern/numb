package numb.app

import numb.translate.{NumberTranslationService, PartBasedTranslationService}
import scaldi.Module

/**
  * Module that specifies the bindings for scaldi for building out the object graph.
  *
  * @author whfern
  */
class NumbModule extends Module {
  bind[NumberTranslationService] to injected[PartBasedTranslationService]
}
