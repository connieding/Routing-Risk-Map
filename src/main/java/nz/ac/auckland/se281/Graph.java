package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * A generic graph class to represent the countries and their connections.
 *
 * @param <T> the type of the country node
 */
public class Graph<T> {
  private Map<T, List<T>> adjacencyCountry;

  public Graph() {
    this.adjacencyCountry = new HashMap<>();
  }

  public void addNode(T countryNode) {
    adjacencyCountry.putIfAbsent(countryNode, new LinkedList<>());
  }

  /**
   * Add an edge between two countries.
   *
   * @param country the source country
   * @param adjCountry the adjacent country
   */
  public void addEdge(T country, T adjCountry) {
    addNode(country);
    addNode(adjCountry);
    adjacencyCountry.get(country).add(adjCountry);
  }

  /**
   * Find the shortest path between two countries using BFS traversal.
   *
   * @param source the source country
   * @param destination the destination country
   * @return the list of countries in the shortest path
   */
  public List<T> findShortestPath(T source, T destination) {
    // Map to keep track of the parent country of each country
    Map<T, T> parentMap = new HashMap<>();
    // List of keep track of visited countries
    List<T> visitedCountry = new ArrayList<>();
    // Queue for BFS traversal
    Queue<T> queue = new LinkedList<>();
    // List to keep track of the path
    List<T> path = new ArrayList<>();

    queue.add(source);
    visitedCountry.add(source);

    // Source country has no parent country
    parentMap.put(source, null);

    // BFS traversal
    while (!queue.isEmpty() && !visitedCountry.contains(destination)) {
      // Dequeue the next country to process
      T currentCountry = queue.poll();

      // check if the destination country is reached
      if (currentCountry.equals(destination)) {
        break;
      }

      // Check the adjacent countries
      for (T country : adjacencyCountry.get(currentCountry)) {

        // if the country is not visited, add to visited and queue.
        if (!visitedCountry.contains(country)) {
          visitedCountry.add(country);
          queue.add(country);

          // set current country as the parent country of the adjacent country
          parentMap.put(country, currentCountry);
        }
      }
    }

    // Reconstruct the path from the parentMap
    // Backtrack from the destination country to the source country where parent country is null
    for (T country = destination; country != null; country = parentMap.get(country)) {
      path.add(country);
    }

    // Reverse the path to get the correct order
    Collections.reverse(path);
    return path;
  }
}
