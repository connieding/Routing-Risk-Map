package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Graph<Country> countryGraph = new Graph<>();
  private Set<Country> countryInfo = new HashSet<>();
  private Set<String> continentNames = new LinkedHashSet<>();
  private List<String> countryNames = new ArrayList<>();
  private List<Country> countriesVisited = new ArrayList<>();
  private int totalTax = 0;
  private Country beginCountry;
  private Country finalCountry;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    // Load the country information and add node to graph
    for (String country : countries) {
      String[] countryData = country.split(",");
      String countryName = countryData[0].trim();
      String countryContinent = countryData[1].trim();
      int countryTax = Integer.parseInt(countryData[2].trim());

      // Add the country to the countryInfo and countryGraph
      countryInfo.add(new Country(countryName, countryContinent, countryTax));
      countryGraph.addNode(new Country(countryName, countryContinent, countryTax));
    }

    // Load the country adjacencies
    for (String adjacency : adjacencies) {
      String[] adjacencyData = adjacency.split(",");

      // Add the edges to the graph
      for (String adjCountry : adjacencyData) {
        try {
          countryGraph.addEdge(
              checkCountryName(adjacencyData[0].trim()), checkCountryName(adjCountry.trim()));
        } catch (CountryNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    // Ask the user to enter the country name
    MessageCli.INSERT_COUNTRY.printMessage();

    while (true) {
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
    while (true) {
      // Ask the user to enter the country name
      String startCountry = Utils.scanner.nextLine();
      startCountry = Utils.capitalizeFirstLetterOfEachWord(startCountry);

      // Print the country information
      try {
        beginCountry = checkCountryName(startCountry);
        break;
      } catch (CountryNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }

    MessageCli.INSERT_DESTINATION.printMessage();
    while (true) {
      // Ask the user to enter the country name
      String endCountry = Utils.scanner.nextLine();
      endCountry = Utils.capitalizeFirstLetterOfEachWord(endCountry);

      try {
        finalCountry = checkCountryName(endCountry);
        break;
      } catch (CountryNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }

    // Clear the lists and variables
    countriesVisited.clear();
    countryNames.clear();
    continentNames.clear();
    totalTax = 0;

    // Find the shortest path between the two countries
    countriesVisited = countryGraph.findShortestPath(beginCountry, finalCountry);

    // If the countriesVisited list is empty, then no path exists
    if (countriesVisited.size() == 1) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    } else {
      // Print the route information
      for (Country country : countriesVisited) {
        countryNames.add(country.getCountryName());
        continentNames.add(country.getContinent());
        totalTax += country.getTaxFees();
      }
      totalTax -= beginCountry.getTaxFees();

      MessageCli.ROUTE_INFO.printMessage("[" + String.join(", ", countryNames) + "]");
      MessageCli.CONTINENT_INFO.printMessage("[" + String.join(", ", continentNames) + "]");
      MessageCli.TAX_INFO.printMessage(Integer.toString(totalTax));
    }
  }

  /**
   * Check if the user country input exists in the countryInfo set.
   *
   * @param countryInput the country name input by the user
   * @return the country object if it exists
   * @throws CountryNotFoundException if the country does not exist
   */
  public Country checkCountryName(String countryInput) throws CountryNotFoundException {

    // Check if the user country input exists in the countryInfo set
    for (Country country : countryInfo) {
      if (country.getCountryName().equals(countryInput)) {
        return country;
      }
    }

    throw new CountryNotFoundException(countryInput);
  }
}
