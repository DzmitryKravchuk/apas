package com.belpost.apas.model.common;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node <T>{
    private T rootElement;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(T rootElement) {
        this.rootElement = rootElement;
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
}
