package com.belpost.apas.service.common;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.persistence.entity.common.LookupEntity;
import com.belpost.apas.persistence.repository.common.LookupRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class NodeServiceImpl<E extends LookupEntity>{
    private final LookupRepository<E> repository;

    @Transactional(readOnly = true)
    public List<E> findChildrenGenerations(Long ancestorId, Integer marginalDescendantLevel) {
        int i = marginalDescendantLevel;
        List <Long> parents = Collections.singletonList(ancestorId);
        List<E> children= null;
        while(i>0){

        }

        return repository.findAll();
    }
}
