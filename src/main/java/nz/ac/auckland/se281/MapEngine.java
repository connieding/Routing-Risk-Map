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
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
