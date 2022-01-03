package com.belpost.apas.service.common;

import com.belpost.apas.model.common.Node;
import com.belpost.apas.model.common.NodeModel;

public interface NodeService<M extends NodeModel> {

    Node<M> buildNodeTree(M root);

}
