package com.belpost.apas.service.common;

import com.belpost.apas.model.common.Node;
import com.belpost.apas.model.common.NodeModel;
import java.util.List;

public interface NodeService<M extends NodeModel> {

    Node<M> getAsTree(Long ancestorId);

    Node<M> getAsBranch(Long rootId, Long targetId);

    List<M> convertToList(Node<M> node);
}
