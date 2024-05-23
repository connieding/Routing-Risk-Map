package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

  public List<Country> shortestPath(Country source, Country destination) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();

    queue.add(source);
    visited.add(source);
    while (!queue.isEmpty()) {
      Country currentCountry = queue.poll();
      for (Country country : adjacencyCountry.get(currentCountry)) {

        if (country.equals(destination)) {
          visited.add(country);
          return visited;
        }

        // if (!visited.contains(country)) {
        //   visited.add(country);
        //   queue.add(country);
        // }
      }
    }
    return visited;
  }
}
