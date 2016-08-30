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
    numberParts.map(translateNumberPartToWords(_)).mkString(" ")
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
    This takes the hundreds part which will be 1-9, looks it up in the dictionary and adds the configured
    hundred word (usually "hundred"), it then converts all the tens in the tens portion into strings via mapping
    their parts to the lookup method, adds them to the list, and then joins these all into one string.
     */
    (List(lookup(hundreds.part), hundredsName) ++ tens.map(_.part).map(lookup(_))).mkString(" ")
  }

  private def composeThousandsString(thousands: Hundreds): String = {
    List(composeHundredsString(thousands.hundreds, thousands.tens), thousandsName).mkString(" ")
  }

  private def composeMillionsString(millions: Hundreds): String = {
    List(composeHundredsString(millions.hundreds, millions.tens), millionsName).mkString(" ")
  }

  private def lookup(string: String): String = {
    tensDictionary.getString(string)
  }

}
