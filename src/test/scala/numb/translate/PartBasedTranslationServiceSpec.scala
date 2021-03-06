package numb.translate

import numb.test.InjectableSpecification
import numb.translate.numberpart.PartBasedTranslationService
import scaldi.Injector

/**
  * Specification for {@link PartBasedTranslationService}
  *
  * @author whfern
  */
class PartBasedTranslationServiceSpec extends InjectableSpecification {

  val partBasedTranslationService : PartBasedTranslationService = inject[PartBasedTranslationService] (by default new PartBasedTranslationService)

  "The part based translation service should " >> {
    "translate 0 to zero" >> {
      "zero" must_== partBasedTranslationService.translate("0")
    }
    "translate 1 to one" >> {
      "one" must_== partBasedTranslationService.translate("1")
    }
    "translate 10 to ten" >> {
      "ten" must_== partBasedTranslationService.translate("10")
    }
    "translate 99 to ninety nine" >> {
      "ninety nine" must_== partBasedTranslationService.translate("99")
    }
    "translate 100 to one hundred" >> {
      "one hundred" must_== partBasedTranslationService.translate("100")
    }
    "translate 199 to one hundred ninety nine" >> {
      "one hundred ninety nine" must_== partBasedTranslationService.translate("199")
    }
    "translate 1000 to one thousand" >> {
      "one thousand" must_== partBasedTranslationService.translate("1000")
    }
    "translate 1555 to one thousand five hundred fifty five" >> {
      "one thousand five hundred fifty five" must_== partBasedTranslationService.translate("1555")
    }
    "translate 277444 to two hundred seventy seven thousand four hundred fourty four" >> {
      "two hundred seventy seven thousand four hundred fourty four" must_== partBasedTranslationService.translate("277444")
    }
    "translate 1000000 to one million" >> {
      "one million" must_== partBasedTranslationService.translate("1000000")
    }
    "translate 1000001 to one million one" >> {
      "one million one" must_== partBasedTranslationService.translate("1000001")
    }
    "omit prefixed zeros from translation" >> {
      "one" must_== partBasedTranslationService.translate("0000001")
    }
    "trim extra space if present before translating" >> {
      "one" must_== partBasedTranslationService.translate("   1 ")
    }
    "handle unexpected input" >> {
      partBasedTranslationService.translate("invalid") must throwA[IllegalArgumentException]
      partBasedTranslationService.translate("") must throwA[IllegalArgumentException]
      partBasedTranslationService.translate("-01924") must throwA[IllegalArgumentException]
    }
  }

}
