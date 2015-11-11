package q3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMinCut {

  public static void main(String[] args) throws IOException {
    RandomMinCut algo = new RandomMinCut();
    algo.setInputFileName(args[0]);
    int rounds;
    try {
      rounds = Integer.parseInt(args[1]);
    } catch (Exception e) {
      rounds = 1000;
    }
    int minCuts = algo.doRandomMinCut(rounds);
    System.out.println("Min cuts after " + rounds + " rounds: " + minCuts);
  }

  private String inputFileName;
  private MinCutGraph graph = new MinCutGraph();

  public int doRandomMinCut(int rounds) throws IOException {
    int minCuts = Integer.MAX_VALUE;
    Random random = new Random();
    for (int i = 0; i < rounds; ++i) {
      loadNodes(inputFileName);
      while (graph.getNodes().size() > 2) {
        String node1Label =
            new ArrayList<>(graph.getNodes().keySet()).get(random.nextInt(graph.getNodes().size()));
        Node node1 = graph.getNodes().get(node1Label);
        List<Node> outLinks = node1.getOutLinks();
        Node node2 = outLinks.get(random.nextInt(outLinks.size()));
        graph.collapseNodes(node1.getLabel(), node2.getLabel());
      }
      int cuts = graph.getNodes().get(new ArrayList<>(graph.getNodes().keySet()).get(0))
          .getOutLinks().size();
      if (cuts < minCuts) {
        minCuts = cuts;
      }
      System.out.println("Round " + i + ":\t" + cuts + "\t" + minCuts);
    }
    return minCuts;
  }

  private void loadNodes(String inputFileName) throws IOException {
    for (String line : Files.readAllLines(Paths.get(inputFileName), StandardCharsets.UTF_8)) {
      String[] parts = line.split("\\s+");
      Integer.parseInt(parts[0]);
      for (int i = 1; i < parts.length; ++i) {
        try {
          Integer.parseInt(parts[i]);
          graph.connectNodes(parts[0], parts[i]);
        } catch (NumberFormatException e) {
          System.err.println(i + " is not a number: " + parts[i]);
        }
      }
    }
  }

  private void setInputFileName(String inputFileName) {
    this.inputFileName = inputFileName;
  }

}
