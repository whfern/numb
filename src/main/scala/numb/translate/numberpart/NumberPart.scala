package numb.translate.numberpart

/**
  * NumberPart is designed to provide tree-like syntax segmenting of a number
  */
abstract class NumberPart


/**
  * Represents all the numbers from 0 to 99 in our dictionary.
  * @param part Number string matching a string in our dictionary
  */
case class Tens(part: String) extends NumberPart

/**
  * Represents all numbers from 0 to 999 in two number parts.
  * @param hundreds Int between 1-9 representing the hundreds place of the hundreds.
  * @param tens List of Tens representing the rest of the hundreds.
  */
case class Hundreds(hundreds: Tens, tens: List[Tens]) extends NumberPart

/**
  * Represents numbers expressed as a power of one thousand.
  * @param hundreds The number in hundreds to prefix
  * @param power The power which this represents.
  */
case class PowerThousands(hundreds: Hundreds, power: Int) extends NumberPart
