package com.belpost.apas.service.common;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.persistence.entity.common.LookupEntity;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class NodeServiceImpl<M extends LookupModel, E extends LookupEntity> implements NodeService<M>{
    private final LookupRepository<E> repository;
    private final LookupMapper<M, E> mapper;

    @Override
    public Map<Integer, List<M>> getChildrenGenerations(Long ancestorId, Integer marginalDescendantLevel) {
        return null;
    }
}
