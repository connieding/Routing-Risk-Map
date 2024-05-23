package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
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
    // Map to keep track of the parent country of each country
    Map<Country, Country> parentMap = new HashMap<>();

    // List of keep track of visited countries
    List<Country> visitedCountry = new ArrayList<>();

    // Queue for BFS traversal
    Queue<Country> queue = new LinkedList<>();

    queue.add(source);
    visitedCountry.add(source);

    // Source country has no parent country
    parentMap.put(source, null);

    // BFS traversal
    while (!queue.isEmpty() && !visitedCountry.contains(destination)) {
      // Dequeue the next country to process
      Country currentCountry = queue.poll();

      // check if the destination country is reached
      if (currentCountry.equals(destination)) {
        break;
      }

      // Check the adjacent countries
      for (Country country : adjacencyCountry.get(currentCountry)) {

        // if the country is not visited, add to visited and queue.
        if (!visitedCountry.contains(country)) {
          // if the country is not visited, add to visited and queue.
          visitedCountry.add(country);
          queue.add(country);

          // set the parent country of the current country
          parentMap.put(country, currentCountry);
        }
      }
    }

    // Reconstruct the path from the parentMap
    List<Country> path = new ArrayList<>();
    for (Country country = destination; country != null; country = parentMap.get(country)) {
      path.add(country);
    }

    Collections.reverse(path);
    return path;
  }
}
