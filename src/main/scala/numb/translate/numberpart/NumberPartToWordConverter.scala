package numb.translate.numberpart

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
  def powerDictionary: Config = inject[Config] ("translation.powers")

  /**
    * Converts a list of number parts to words.
    *
    * @param numberParts NumberPart objects to convert.
    * @return String containing the words.
    */
  def translateNumberPartsToWords(numberParts: List[NumberPart]): String = {
    // Translate each part into words, get rid of empty results, and join them back together with a space between each.
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
      case PowerThousands(hundreds, power) => composePowerThousandsString(hundreds, power)
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
    // In the case of zero in the hundreds place, do not add 'hundred'
    if (hundreds.part.toInt == 0) {
      List()
    } else {
      List(nonZeroLookup(hundreds.part), hundredsName)
    }
  }

  private def composePowerThousandsString(thousands: Hundreds, power: Int): String = {
    // In the case that we have 0 thousands, do not add a thousands word.
    if (thousands.hundreds.part.toInt == 0 && !thousands.tens.exists(_.part.toInt != 0)) {
      return ""
    }
    // Otherwise, compose a hundreds for the number of thousands we have, and filter empty results before joining.
    List(composeHundredsString(thousands.hundreds, thousands.tens), lookupPower(power)).filter(_.nonEmpty).mkString(" ")
  }


  private def nonZeroLookup(string: String): String = {
    // Ensures that we do not end up with zero hundred thousand, etc.
    if (string.toInt == 0) {
      ""
    } else {
      lookup(string)
    }
  }

  private def lookup(string: String): String = {
    tensDictionary.getString(string.toInt.toString)
  }

  private def lookupPower(power: Int): String = {
    powerDictionary.getString(power.toString)
  }

}
