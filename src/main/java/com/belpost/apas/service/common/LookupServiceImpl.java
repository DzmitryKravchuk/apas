package com.belpost.apas.service.common;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.persistence.entity.common.LookupEntity;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class LookupServiceImpl<M extends LookupModel, E extends LookupEntity> {
    private final LookupRepository<E> repository;

    @Transactional(readOnly = true)
    public E findByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(
            String.format("%s not found with code: %s", getEntityInfo(), code)));
    }

    @Transactional(readOnly = true)
    public E findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
            String.format("%s not found with id: %s", getEntityInfo(), id)));
    }

    @Transactional(readOnly = true)
    public List<E> findAll() {
        return repository.findAll();
    }

    @SuppressWarnings("unchecked")
    String getEntityInfo() {
        return ((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1])
            .getSimpleName();
    }
}
