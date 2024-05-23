package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  Graph<Country> countryGraph = new Graph<>();
  Set<Country> countryInfo = new HashSet<>();
  Country beginCountry;
  Country finalCountry;

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

      countryInfo.add(new Country(countryName, countryContinent, countryTax));
      countryGraph.addNode(new Country(countryName, countryContinent, countryTax));
    }

    // Load the country adjacencies
    for (String adjacency : adjacencies) {
      String[] adjacencyData = adjacency.split(",");

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

    // Find the shortest path between the two countries
    List<Country> countriesVisited = countryGraph.shortestPath(beginCountry, finalCountry);

    // need to get the country names from the list of Country objects
    List<String> countryNames = new ArrayList<>();
    Set<String> continentNames = new LinkedHashSet<>();
    for (Country country : countriesVisited) {
      countryNames.add(country.getCountryName());
      continentNames.add(country.getContinent());
    }

    MessageCli.ROUTE_INFO.printMessage("[" + String.join(", ", countryNames) + "]");
    MessageCli.CONTINENT_INFO.printMessage("[" + String.join(", ", continentNames) + "]");
  }

  public Country checkCountryName(String countryInput) throws CountryNotFoundException {

    for (Country country : countryInfo) {
      if (country.getCountryName().equals(countryInput)) {
        return country;
      }
    }

    throw new CountryNotFoundException(countryInput);
  }
}
