package numb.translate.numberpart

import numb.translate.NumberTranslationService
import scaldi.{Injectable, Injector}

/**
  * Implementation of number translation utilizing {@link NumberPart} case classes to decompose the number and build it
  * back up into a composite string.
  *
  * @author whfern
  */
class PartBasedTranslationService(implicit injector: Injector) extends NumberTranslationService with Injectable {

  def stringToNumberPartConverter: DigitStringToNumberPartConverter = inject[DigitStringToNumberPartConverter]
  def numberPartToWordConverter: NumberPartToWordConverter = inject[NumberPartToWordConverter]

  override def translate(numberString: String): String = {
    // Guard statements for invalid input
    if (null == numberString) {
      throw new IllegalArgumentException("Number supplied to part-based translate must not be null.")
    }

    def trimmedString: String = numberString.trim()

    if (containsNonDigits(trimmedString)) {
      throw new IllegalArgumentException("Number supplied to part-based translate must not contain non digits.")
    }
    if (trimmedString.isEmpty) {
      throw new IllegalArgumentException("Number supplied to part-based translate must not be empty.")
    }

    // Clean, expand, and rejoin the string.
    def numberParts: List[NumberPart] = stringToNumberPartConverter.convertCleanStringToNumberParts(trimmedString)
    numberPartToWordConverter.translateNumberPartsToWords(numberParts)
  }

  private def containsNonDigits(string: String): Boolean = {
    string.matches("\\D*")
  }
}
