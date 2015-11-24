package q5;

import java.util.HashMap;
import java.util.Map;

public class DijkstraNode {

  private String label;
  private Map<DijkstraNode, Integer> outLinks = new HashMap<>();
  private Map<DijkstraNode, Integer> inLinks = new HashMap<>();

  public DijkstraNode(String label) {
    setLabel(label);
  }

  public void addOutLink(DijkstraNode node, int length) {
    if (outLinks.get(node) == null) {
      outLinks.put(node, length);
    } else if (outLinks.get(node) > length) {
      outLinks.put(node, length);
    }
  }

  public Map<DijkstraNode, Integer> getOutLinks() {
    return outLinks;
  }

  public void addInLink(DijkstraNode node, int length) {
    if (inLinks.get(node) == null) {
      inLinks.put(node, length);
    } else if (inLinks.get(node) > length) {
      inLinks.put(node, length);
    }
  }

  public Map<DijkstraNode, Integer> getInLinks() {
    return inLinks;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "Node[" + label + "]";
  }

}
