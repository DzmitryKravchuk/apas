package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeTypeMapper;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.persistence.repository.OfficeTypeRepository;
import com.belpost.apas.service.common.LookupServiceImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OfficeTypeServiceImpl
    extends LookupServiceImpl<OfficeType, OfficeTypeModel>
    implements OfficeTypeService {

    public OfficeTypeServiceImpl(OfficeTypeRepository repository, OfficeTypeMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeTypeModel getByCode(String code) {
        log.info("getByCode: {}", code);
        return super.getByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeTypeModel getById(Long id) {
        log.info("getById: {}", id);
        return super.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfficeTypeModel> getAll() {
        log.info("getAll()");
        return super.getAll();
    }
}
