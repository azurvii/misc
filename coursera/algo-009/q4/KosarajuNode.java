package q4;

import java.util.ArrayList;
import java.util.List;

public class KosarajuNode {

  private String label;
  private List<KosarajuNode> outLinks = new ArrayList<>();
  private List<KosarajuNode> inLinks = new ArrayList<>();
  private KosarajuNode groupLead = null;
  private int finishOrder = -1;

  public KosarajuNode(String label) {
    setLabel(label);
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<KosarajuNode> getOutLinks() {
    return outLinks;
  }

  public void addOutLink(KosarajuNode node) {
    if (node != null) {
      outLinks.add(node);
    }
  }

  public List<KosarajuNode> getInLinks() {
    return inLinks;
  }

  public void addInLink(KosarajuNode node) {
    if (node != null) {
      inLinks.add(node);
    }
  }

  public KosarajuNode getGroupLead() {
    return groupLead;
  }

  public void setGroupLead(KosarajuNode groupLead) {
    this.groupLead = groupLead;
  }

  public int getFinishOrder() {
    return finishOrder;
  }

  public void setFinishOrder(int finishOrder) {
    this.finishOrder = finishOrder;
  }

}
