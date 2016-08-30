package numb.translate.numberpart

/**
  * Expands a string into a collection of number parts.
  *
  * @author whfern
  */
class DigitStringToNumberPartConverter {

  /**
    * Expands a digits-only string into a list of number parts based on length.
    *
    * @param numberString String to expand.
    * @return List of NumberPart objects matching string expansion.
    */
  def convertCleanStringToNumberParts(numberString: String): List[NumberPart] = {
    numberString match {
      case matchedString if 1 to 2 contains matchedString.length =>
        convertTens(matchedString)
      case matchedString if 3 equals matchedString.length =>
        List(convertHundreds(matchedString))
      case matchedString if 3 < matchedString.length =>
        convertPowerThousands(matchedString, (matchedString.length - 1)/ 3)
      case _ =>
        throw new RuntimeException("Unexpected string length while decomposing input string. Is the string being cleaned right?")
    }
  }

  private def convertTens(numberString: String): List[Tens] = {
    // If we have an empty string, we should default to returning no tens.
    if (numberString.isEmpty) {
      return List()
    }
    def number: Int = numberString.toInt

    // If the number is less than twenty or divisible by ten, it's in our dictionary.
    if (number <= 20 || number % 10 == 0) {
      List(Tens(numberString))
    } else {
      // Otherwise we cut it up into two numbers that will be in our dictionary.
      List(Tens(numberString.charAt(0) + "0"), Tens(numberString.substring(1)))
    }
  }

  private def convertHundreds(numberString: String): Hundreds = {
    // Either we have a hundred with three digits or we have a hundred that is actually just a tens.
    if (numberString.length == 3) {
      Hundreds(
        hundreds = Tens(numberString.substring(0, 1)),
        tens = convertTens(numberString.substring(1))
      )
    } else {
      Hundreds(
        hundreds = Tens("0"),
        tens = convertTens(numberString)
      )
    }
  }

  private def convertPowerThousands(numberString: String, power: Int): List[NumberPart] = {
    // Segment the string based on length, and convert piecewise into a list of parts.
    def thousandsIndex = numberString.length - 3 * power
    def thousandsPart = numberString.substring(Math.max(0, numberString.length - 3 * (power + 1)), thousandsIndex)
    if (power == 1) {
      def hundredsPart = numberString.substring(thousandsIndex)
      List(PowerThousands(convertHundreds(thousandsPart), power), convertHundreds(hundredsPart))
    } else {
      List(PowerThousands(convertHundreds(thousandsPart), power)) ++ convertPowerThousands(numberString, power - 1)
    }
  }

}
