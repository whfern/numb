package numb.translate

/**
  * Trait that defines the API for translating numbers.
  *
  * @author whfern
  */
trait NumberTranslationService {

  /**
    * Takes an stringly typed integer between 0 and 1000001 and converts it into a string matching the english representation of the number.
    * String must have only digits within, but may contain preceding zeroes and can be untrimmed.
    * For example, "100234" -> "one hundred thousand two hundred thirty four"
    *
    * @param numberString String containing only digits.
    * @return String containing converted words representing the number.
    */
  def translate(numberString: String) : String
}
