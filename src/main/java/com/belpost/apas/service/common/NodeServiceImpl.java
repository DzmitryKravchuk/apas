package com.belpost.apas.service.common;

import com.belpost.apas.persistence.entity.common.NodeEntity;
import com.belpost.apas.persistence.repository.common.NodeRepository;
import java.lang.reflect.ParameterizedType;
import java.util.List;
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
    public List<E> findChildrenGenerations(Long... ancestorIds) {
        return repository.findAllByParentIdIn(ancestorIds);
    }

    @SuppressWarnings("unchecked")
    String getEntityInfo() {
        return ((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1])
            .getSimpleName();
    }
}
