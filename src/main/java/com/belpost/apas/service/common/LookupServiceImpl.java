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
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public abstract class LookupServiceImpl <M extends LookupModel, E extends LookupEntity> implements LookupService<M, E>{
    private final LookupRepository<E> repository;
    private final LookupMapper<M, E> mapper;

    @Override
    @Transactional(readOnly = true)
    public M getByCode(String code) {
        log.info("getByCode: {}", code);
        return repository.findByCode(code).map(mapper::mapToModel)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("%s not found with code: %s", getEntityInfo(), code)));
    }

    @Override
    @Transactional(readOnly = true)
    public List <M> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::mapToModel).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    String getEntityInfo() {
        return ((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1])
            .getSimpleName();
    }
}
