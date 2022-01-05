package com.belpost.apas.service;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.service.common.NodeService;
import java.util.List;

public interface OfficeService extends NodeService <OfficeModel>{

    OfficeModel getByCode (String  code);

    OfficeModel getById (Long  id);

    List<OfficeModel> getAll();

    Node<OfficeModel> getAsTree(Long ancestorId);

    void printTree(Node<OfficeModel> node, String appender);
}
