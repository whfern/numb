package numb.translate

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
      case matchedString if 4 to 6 contains matchedString.length =>
        convertThousands(matchedString)
      case matchedString if 7 equals matchedString.length =>
        convertMillions(matchedString.substring(0, 1)) ++ convertThousands(matchedString)
      case _ =>
        throw new RuntimeException("Unexpected string length while decomposing input string. Is the string being cleaned right?")
    }
  }

  private def convertTens(numberString: String): List[Tens] = {
    if (numberString.isEmpty) {
      return List()
    }
    def number: Int = numberString.toInt
    if (number <= 20 || number % 10 == 0) {
      List(Tens(numberString))
    } else {
      List(Tens(numberString.charAt(0) + "0"), Tens(numberString.substring(1)))
    }
  }

  private def convertHundreds(numberString: String): Hundreds = {
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

  private def convertThousands(numberString: String): List[NumberPart] = {
    def thousandsIndex = numberString.length - 3
    def thousandsPart = numberString.substring(0, thousandsIndex)
    def hundredsPart = numberString.substring(thousandsIndex)

    List(Thousands(convertHundreds(thousandsPart)), convertHundreds(hundredsPart))
  }

  private def convertMillions(numberString: String): List[NumberPart] = {
    def millionsIndex = numberString.length - 6
    def millionsPart = numberString.substring(0, millionsIndex)
    def thousandsPart = numberString.substring(millionsIndex)

    List(Millions(convertHundreds(millionsPart))) ++ convertThousands(thousandsPart)
  }

}
