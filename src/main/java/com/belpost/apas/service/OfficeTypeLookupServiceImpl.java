package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeTypeMapper;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.persistence.repository.OfficeTypeRepository;
import com.belpost.apas.service.common.LookupService;
import com.belpost.apas.service.common.LookupServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficeTypeLookupServiceImpl extends
    LookupServiceImpl<OfficeType> implements LookupService<OfficeTypeModel> {

    private final OfficeTypeMapper mapper;

    public OfficeTypeLookupServiceImpl(OfficeTypeRepository repository, OfficeTypeMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    public OfficeTypeModel getByCode(String code) {
        log.info("getByCode: {}", code);
        return mapper.mapToModel(findByCode(code));
    }

    @Override
    public OfficeTypeModel getById(Long id) {
        log.info("getById: {}", id);
        return mapper.mapToModel(findById(id));
    }

    @Override
    public List<OfficeTypeModel> getAll() {
        log.info("getAll()");
        return findAll().stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());
    }

}
