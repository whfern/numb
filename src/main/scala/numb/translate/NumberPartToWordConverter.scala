package numb.translate

import com.typesafe.config.Config
import scaldi.{Injectable, Injector}

/**
  * Converts a number part to a string.
  *
  * @author whfern
  */
class NumberPartToWordConverter(implicit injector: Injector) extends Injectable {

  def tensDictionary: Config = inject[Config] ("translation.tens")
  def hundredsName: String = inject[String] ("translation.hundreds")
  def thousandsName: String = inject[String] ("translation.thousands")
  def millionsName: String = inject[String] ("translation.millions")

  /**
    * Converts a list of number parts to words.
    *
    * @param numberParts NumberPart objects to convert.
    * @return String containing the words.
    */
  def translateNumberPartsToWords(numberParts: List[NumberPart]): String = {
    numberParts.map(translateNumberPartToWords(_)).filter(_.nonEmpty).mkString(" ")
  }

  /**
    * Converts a single number part to words.
    *
    * @param numberPart NumberPart to convert.
    * @return String containing the words.
    */
  def translateNumberPartToWords(numberPart: NumberPart): String = {
    numberPart match {
      case Tens(part) => tensDictionary.getString(part)
      case Hundreds(hundreds, tens) => composeHundredsString(hundreds, tens)
      case Thousands(hundreds) => composeThousandsString(hundreds)
      case Millions(hundreds) => composeMillionsString(hundreds)
    }
  }

  private def composeHundredsString(hundreds: Tens, tens: List[Tens]): String = {
    /*
    This takes the hundreds part if it is 1-9, looks it up in the dictionary and adds the configured
    hundred word (usually "hundred"), it then converts all the tens in the tens portion into strings via mapping
    their parts to the lookup method, adds them to the list, and then joins these all into one string.
     */
    (composeHundredsPrefix(hundreds) ++ tens.map(_.part).map(nonZeroLookup(_))).filter(_.nonEmpty).mkString(" ")
  }

  private def composeHundredsPrefix(hundreds: Tens): List[String] = {
    if (hundreds.part.toInt == 0) {
      List()
    } else {
      List(nonZeroLookup(hundreds.part), hundredsName)
    }
  }

  private def composeThousandsString(thousands: Hundreds): String = {
    List(composeHundredsString(thousands.hundreds, thousands.tens), thousandsName).filter(_.nonEmpty).mkString(" ")
  }

  private def composeMillionsString(millions: Hundreds): String = {
    List(composeHundredsString(millions.hundreds, millions.tens), millionsName).filter(_.nonEmpty).mkString(" ")
  }

  private def nonZeroLookup(string: String): String = {
    if (string.toInt == 0) {
      ""
    } else {
      lookup(string)
    }
  }

  private def lookup(string: String): String = {
    tensDictionary.getString(string)
  }

}
