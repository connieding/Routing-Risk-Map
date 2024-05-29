package nz.ac.auckland.se281;

/** Exception thrown when a country is not found. */
public class CountryNotFoundException extends Exception {

  public CountryNotFoundException(String input) {
    super(MessageCli.INVALID_COUNTRY.getMessage(input));
  }
}
