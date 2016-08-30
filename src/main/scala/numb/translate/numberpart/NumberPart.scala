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
  * Represents the thousands in a number.
  * @param thousands Hundreds representing the number of thousands in a number.
  */
case class Thousands(thousands: Hundreds) extends NumberPart

/**
  * Represents the millions in a number.
  * @param millions Hundreds representing the number of millions in a number.
  */
case class Millions(millions: Hundreds) extends NumberPart
