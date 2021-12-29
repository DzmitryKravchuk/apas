package com.belpost.apas.service.common;

import com.belpost.apas.model.common.Node;

public interface NodeService <M>{

    Node<M> buildNodeTree(M root);
}
