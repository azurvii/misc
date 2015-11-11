package q3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a node in a graph for contraction.
 */
public class Node {

  private String label;
  private List<Node> outLinks = new ArrayList<>();
  private List<Node> inLinks = new ArrayList<>();

  public Node(String label) {
    setLabel(label);
  }

  public void collapseWith(Node node) {
    outLinks.addAll(node.outLinks);
    inLinks.addAll(node.inLinks);
    for (int i = inLinks.size() - 1; i >= 0; --i) {
      Node inLink = inLinks.get(i);
      if (inLink == this || inLink == node) {
        inLinks.remove(i);
      } else {
        Collections.replaceAll(inLink.outLinks, node, this);
      }
    }
    for (int i = outLinks.size() - 1; i >= 0; --i) {
      Node outLink = outLinks.get(i);
      if (outLink == this || outLink == node) {
        outLinks.remove(i);
      } else {
        Collections.replaceAll(outLink.inLinks, node, this);
      }
    }
  }

  public void addOutLink(Node node) {
    outLinks.add(node);
  }

  public List<Node> getOutLinks() {
    return outLinks;
  }

  public void addInLink(Node node) {
    inLinks.add(node);
  }

  public List<Node> getInLinks() {
    return inLinks;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
