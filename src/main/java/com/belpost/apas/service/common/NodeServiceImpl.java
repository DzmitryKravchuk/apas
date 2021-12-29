package com.belpost.apas.service.common;

import com.belpost.apas.persistence.entity.common.LookupEntity;
import com.belpost.apas.persistence.entity.common.NodeEntity;
import com.belpost.apas.persistence.repository.common.NodeRepository;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class NodeServiceImpl<E extends NodeEntity> extends LookupServiceImpl<E> {
    private final NodeRepository<E> repository;

    public NodeServiceImpl(NodeRepository<E> repository) {
        super(repository);
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<E> findDescendents(Long ancestorId) {
        Set<Long> ancestorIds = new HashSet<>(Collections.singleton(ancestorId));
        Set<Long> parentIds = new HashSet<>(Collections.singleton(ancestorId));
        while (!repository.findAllByParentIdIn(parentIds.toArray(new Long[0])).isEmpty()) {
            // get all children for next generation of parents
            List<E> children = repository.findAllByParentIdIn(parentIds.toArray(new Long[0]));
            parentIds = children.stream()
                .map(LookupEntity::getId).collect(Collectors.toSet());
            // add parent ids to ancestor id list
            ancestorIds.addAll(parentIds);
        }
        return repository.findAllByParentIdIn(ancestorIds.toArray(new Long[0]));
    }

    @SuppressWarnings("unchecked")
    String getEntityInfo() {
        return ((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1])
            .getSimpleName();
    }
}
