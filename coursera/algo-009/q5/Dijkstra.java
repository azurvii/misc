package q5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Computes shortest path using Dijkstra's algorithm.
 */
public class Dijkstra {

  public static void main(String[] args) throws IOException {
    String sourceNodeLabel = "1";
    String targetDestinations = "7,37,59,82,99,115,133,165,188,197";
    // String targetDestinations = "1,2,3,4,5,6,7,8";

    Dijkstra algo = new Dijkstra();
    algo.loadGraph(args[0]);
    System.out.println("Graph loaded.");
    Map<DijkstraNode, Integer> dists = algo.calculateShortestDistancesFrom(sourceNodeLabel);
    System.out.println("Distances calculated for " + sourceNodeLabel + ".");

    String[] parts = targetDestinations.split(",");
    for (String dst : parts) {
      System.out.print(dists.get(algo.graph.getNodes().get(dst)) + ",");
    }
    System.out.println();
  }

  private static final int EXPECTED_NODES = 200;
  private DijkstraGraph graph = new DijkstraGraph(EXPECTED_NODES);

  public void loadGraph(String fileName) throws IOException {
    for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
      String[] parts = line.split("\\s+");
      String nodeLabel = parts[0];
      for (int i = 1; i < parts.length; ++i) {
        String[] nodeParts = parts[i].split(",");
        graph.connectNodes(nodeLabel, nodeParts[0], Integer.parseInt(nodeParts[1]));
      }
    }
  }

  public Map<DijkstraNode, Integer> calculateShortestDistancesFrom(String nodeLabel) {
    Map<DijkstraNode, Integer> minDistances = new HashMap<>(EXPECTED_NODES);
    Map<DijkstraNode, Integer> knownDistances = new HashMap<>(EXPECTED_NODES);

    DijkstraNode sourceNode = graph.getNodes().get(nodeLabel);
    minDistances.put(sourceNode, 0);
    for (DijkstraNode outLink : sourceNode.getOutLinks().keySet()) {
      int distance = sourceNode.getOutLinks().get(outLink);
      knownDistances.put(outLink, distance);
    }

    while (!knownDistances.isEmpty()) {
      DijkstraNode shortestNode = null;
      int shortestDistance = Integer.MAX_VALUE;
      for (DijkstraNode node : knownDistances.keySet()) {
        if (knownDistances.get(node) < shortestDistance) {
          shortestNode = node;
          shortestDistance = knownDistances.get(node);
        }
      }

      knownDistances.remove(shortestNode);

      if (!minDistances.containsKey(shortestNode)) {
        minDistances.put(shortestNode, shortestDistance);

        for (DijkstraNode outLink : shortestNode.getOutLinks().keySet()) {
          int outLinkDistance =
              shortestNode.getOutLinks().get(outLink) + minDistances.get(shortestNode);
          if (knownDistances.get(outLink) == null) {
            knownDistances.put(outLink, outLinkDistance);
          } else {
            knownDistances.put(outLink, Math.min(outLinkDistance, knownDistances.get(outLink)));
          }
        }
      }
    }

    return minDistances;
  }

}
