package q5;

import java.util.HashMap;
import java.util.Map;

public class DijkstraGraph {

  private Map<String, DijkstraNode> nodes;

  public DijkstraGraph(int expectedNodes) {
    nodes = new HashMap<String, DijkstraNode>(expectedNodes);
  }
  
  private DijkstraNode addNode(String nodeLabel) {
    DijkstraNode node = new DijkstraNode(nodeLabel);
    nodes.put(nodeLabel, node);
    return node;
  }

  public void connectNodes(String node1Label, String node2Label, int distance) {
    DijkstraNode node1 = nodes.get(node1Label);
    if (node1 == null) {
      node1 = addNode(node1Label);
    }
    DijkstraNode node2 = nodes.get(node2Label);
    if (node2 == null) {
      node2 = addNode(node2Label);
    }
    node1.addOutLink(node2, distance);
    node2.addInLink(node1, distance);
  }

  public Map<String, DijkstraNode> getNodes() {
    return nodes;
  }


}
