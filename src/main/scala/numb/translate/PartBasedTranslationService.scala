package numb.translate

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
    if (trimmedString.toInt < 0 || trimmedString.toInt > 1000001) {
      throw new IllegalArgumentException("Number supplied to part-based translate must be between 0 and 1000001")
    }

    def cleanedString: String = trimLeadingZeroes(trimmedString)
    def numberParts: List[NumberPart] = stringToNumberPartConverter.convertCleanStringToNumberParts(cleanedString)
    numberPartToWordConverter.translateNumberPartsToWords(numberParts)
  }

  private def containsNonDigits(string: String): Boolean = {
    string.matches("\\D*")
  }

  private def trimLeadingZeroes(string: String): String = {
    string.toInt.toString
  }
}
