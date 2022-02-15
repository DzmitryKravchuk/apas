package com.belpost.apas.service;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.service.common.NodeService;

public interface OfficeService extends NodeService <OfficeModel>{

    OfficeModel getByCode (String  code);

    OfficeModel getNodeById(Long  id);

    OfficeModel getById (Long  id);

    Node<OfficeModel> getAsTree(Long ancestorId);

    Node<OfficeModel> getAsBranch(Long rootId, Long targetId);

    void printTree(Node<OfficeModel> node, String appender);
}
