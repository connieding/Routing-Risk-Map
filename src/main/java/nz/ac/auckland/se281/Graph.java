package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph<Country> {
  private Map<Country, List<Country>> adjacencyCountry;

  public Graph() {
    this.adjacencyCountry = new HashMap<>();
  }

  public void addNode(Country countryNode) {
    adjacencyCountry.putIfAbsent(countryNode, new LinkedList<>());
  }

  public void addEdge(Country country, Country adjCountry) {
    addNode(country);
    addNode(adjCountry);
    adjacencyCountry.get(country).add(adjCountry);
  }
}
