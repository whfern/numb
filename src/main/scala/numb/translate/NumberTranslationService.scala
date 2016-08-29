package numb.translate

/**
  * Trait that defines the API for translating numbers.
  */
trait NumberTranslationService {
  def translate(numberString: String) : String
}
