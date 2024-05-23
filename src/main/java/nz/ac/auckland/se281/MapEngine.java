package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  Set<Country> countryInfo = new HashSet<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    // Load the country information
    for (String country : countries) {
      String[] countryData = country.split(",");
      String countryName = countryData[0].trim();
      String countryContinent = countryData[1].trim();
      int countryTax = Integer.parseInt(countryData[2].trim());

      countryInfo.add(new Country(countryName, countryContinent, countryTax));
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here

    while (true) {
      // Ask the user to enter the country name
      MessageCli.INSERT_COUNTRY.printMessage();
      String countryInput = Utils.scanner.nextLine();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(countryInput);

      // Print the country information
      try {
        Country country = checkCountryName(countryInput);
        MessageCli.COUNTRY_INFO.printMessage(
            country.getCountryName(),
            country.getContinent(),
            Integer.toString(country.getTaxFees()));
        break;
      } catch (CountryNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();
    String startCountry = Utils.scanner.nextLine();

    MessageCli.INSERT_DESTINATION.printMessage();
    String endCountry = Utils.scanner.nextLine();
  }

  public Country checkCountryName(String countryInput) throws CountryNotFoundException {
    // Print out the country information
    for (Country country : countryInfo) {
      if (country.getCountryName().equals(countryInput)) {
        return country;
      }
    }

    throw new CountryNotFoundException(countryInput);
  }
}
