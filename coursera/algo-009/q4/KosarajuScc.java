package q4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class KosarajuScc {

  private static final int EXPECTED_NODES = 1000000;
  private static Date stamp;

  private KosarajuGraph graph = new KosarajuGraph(EXPECTED_NODES);

  private int finishCounter;
  private Set<KosarajuNode> explored;
  private KosarajuNode groupLead;
  private KosarajuNode[] secondPassOrder;
  private PriorityQueue<Integer> sccCounts;

  public static void main(String[] args) throws IOException {
    // This may need -Xss10m VM arguments or something to avoid stack overflow in recursive calls.
    markTimestamp();
    KosarajuScc algo = new KosarajuScc();
    logWithStamp("Loading graph...");
    algo.loadGraph(args[0]);
    logWithStamp("Loaded " + algo.getGraph().getNodes().size() + " node(s).");
    algo.firstPass();
    logWithStamp("Completed first pass.");
    algo.secondPass();
    logWithStamp("Completed Second pass.");
    System.out.println("Total SCCs found: " + algo.getSccCounts().size() + ". Top 5 sizes:");
    for (int i = 0; i < 5; ++i) {
      System.out.print(algo.getSccCounts().poll());
      if (i < 4) {
        System.out.print(",");
      }
    }
  }

  public void firstPass() {
    finishCounter = 0;
    explored = new HashSet<>(EXPECTED_NODES);
    for (KosarajuNode node : graph.getNodes().values()) {
      if (!explored.contains(node)) {
        groupLead = node;
        dfsInLinks(node);
      }
    }
  }

  public void secondPass() {
    explored = new HashSet<>(EXPECTED_NODES);
    orderNodesAfterFirstPass();
    sccCounts = new PriorityQueue<>(100, Collections.reverseOrder());
    for (int i = secondPassOrder.length - 1; i >= 0; --i) {
      KosarajuNode node = secondPassOrder[i];
      if (!explored.contains(node)) {
        sccCounts.add(dfsOutLinks(node));
      }
    }
  }

  private void dfsInLinks(KosarajuNode node) {
    explored.add(node);
    node.setGroupLead(groupLead);
    for (KosarajuNode inNode : node.getInLinks()) {
      if (!explored.contains(inNode)) {
        dfsInLinks(inNode);
      }
    }
    node.setFinishOrder(finishCounter++);
  }

  private int dfsOutLinks(KosarajuNode node) {
    explored.add(node);
    int nodeCount = 1;
    for (KosarajuNode outNode : node.getOutLinks()) {
      if (!explored.contains(outNode)) {
        nodeCount += dfsOutLinks(outNode);
      }
    }
    return nodeCount;
  }

  private void orderNodesAfterFirstPass() {
    secondPassOrder = new KosarajuNode[graph.getNodes().size()];
    for (KosarajuNode node : graph.getNodes().values()) {
      int order = node.getFinishOrder();
      if (order >= 0) {
        secondPassOrder[order] = node;
      } else {
        System.err.println(node.getLabel() + " was not properly processed in 1st pass!");
      }
    }
  }

  public void loadGraph(String fileName) throws IOException {
    for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
      String[] parts = line.split("\\s+");
      if (parts.length != 2) {
        throw new RuntimeException("Invalid input file format!");
      }
      graph.connectNodes(parts[0], parts[1]);
    }
  }

  private static void markTimestamp() {
    stamp = new Date();
  }

  private static void logWithStamp(String message) {
    System.out.println("[" + (new Date().getTime() - stamp.getTime()) + "]\t" + message);
  }

  public KosarajuGraph getGraph() {
    return graph;
  }

  public PriorityQueue<Integer> getSccCounts() {
    return sccCounts;
  }

}
