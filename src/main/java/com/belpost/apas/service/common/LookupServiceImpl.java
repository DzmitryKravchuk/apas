package com.belpost.apas.service.common;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.persistence.entity.common.LookupEntity;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class LookupServiceImpl<E extends LookupEntity, M extends LookupModel>
    implements LookupService<M>{

    private final LookupRepository<E> repository;
    private final LookupMapper<M, E> mapper;

    @Override
    public M getByCode(String code) {
        return mapper.mapToModel(repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(
            String.format("%s not found with code: %s", getEntityInfo(), code))));
    }

    @Override
    public M getById(Long id) {
        return mapper.mapToModel(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
            String.format("%s not found with id: %s", getEntityInfo(), id))));
    }

    @Override
    public List<M> getAll() {
        return repository.findAll().stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    String getEntityInfo() {
        return ((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0])
            .getSimpleName();
    }
}
