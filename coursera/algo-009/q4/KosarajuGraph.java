package q4;

import java.util.HashMap;
import java.util.Map;

public class KosarajuGraph {

  private int expectedNodes;
  private Map<String, KosarajuNode> nodes = new HashMap<>(expectedNodes);

  public KosarajuGraph(int expectedNodes) {
    this.expectedNodes = expectedNodes;
  }

  private KosarajuNode addNode(String nodeLabel) {
    KosarajuNode node = new KosarajuNode(nodeLabel);
    nodes.put(nodeLabel, node);
    return node;
  }

  public void connectNodes(String node1Label, String node2Label) {
    KosarajuNode node1 = nodes.get(node1Label);
    if (node1 == null) {
      node1 = addNode(node1Label);
    }
    KosarajuNode node2 = nodes.get(node2Label);
    if (node2 == null) {
      node2 = addNode(node2Label);
    }
    node1.addOutLink(node2);
    node2.addInLink(node1);
  }

  public Map<String, KosarajuNode> getNodes() {
    return nodes;
  }

}
