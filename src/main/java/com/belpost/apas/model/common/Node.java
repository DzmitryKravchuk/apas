package com.belpost.apas.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"nodeElement", "children"})
public class Node <T extends NodeModel>{

    @JsonProperty("nodeElement")
    private T nodeElement;

    @JsonProperty("children")
    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(T nodeElement) {
        this.nodeElement = nodeElement;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

//    @Override
//    public boolean equals(Object o) {
 //       if (this == o) {
 //           return true;
  //      }
  //      if (o == null || getClass() != o.getClass()) {
  //          return false;
 //       }

  //      Node<?> node = (Node<?>) o;

  //      if (!nodeElement.equals(node.nodeElement)) {
  //          return false;
 //       }
  //      return children != null ? children.equals(node.children) : node.children == null;
 //   }

 //   @Override
 //   public int hashCode() {
 //       int result = 0;
 //       if (nodeElement!=null){
 //       result = nodeElement.hashCode();
 //       result = 31 * result + (children != null ? children.hashCode() : 0);
//        }
//        return result;
 //   }
}
