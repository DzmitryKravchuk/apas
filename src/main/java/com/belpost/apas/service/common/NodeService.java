package com.belpost.apas.service.common;

import java.util.List;
import java.util.Map;

public interface NodeService <M>{

    Map<Integer, List<M>> getChildrenGenerations(Long ancestorId, Integer marginalDescendantLevel);
}
