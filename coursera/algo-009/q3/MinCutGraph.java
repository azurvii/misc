package q3;

import java.util.HashMap;
import java.util.Map;

public class MinCutGraph {

  private Map<String, Node> nodes = new HashMap<>();

  private Node addNode(String nodeLabel) {
    Node node = new Node(nodeLabel);
    nodes.put(nodeLabel, node);
    return node;
  }

  public void connectNodes(String node1Label, String node2Label) {
    Node node1 = nodes.get(node1Label);
    if (node1 == null) {
      node1 = addNode(node1Label);
    }
    Node node2 = nodes.get(node2Label);
    if (node2 == null) {
      node2 = addNode(node2Label);
    }
    node1.addOutLink(node2);
    node2.addInLink(node1);
  }

  public void collapseNodes(String node1Label, String node2Label) {
    Node node1 = nodes.get(node1Label);
    Node node2 = nodes.get(node2Label);
    if (node1 != null && node2 != null) {
      node1.collapseWith(node2);
      nodes.remove(node2Label);
    } else {
      System.err.println("Failed to collapse " + node1Label + "+" + node2Label);
    }
  }

  public Map<String, Node> getNodes() {
    return nodes;
  }

}
