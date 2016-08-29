package numb.translate

/**
  * Implementation of number translation utilizing {@link NumberPart} case classes to decompose the number and build it
  * back up into a composite string.
  *
  * @author whfern
  */
class PartBasedTranslationService extends NumberTranslationService {
  override def translate(numberString: String): String = ???
}
