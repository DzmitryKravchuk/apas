package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeTypeMapper;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.persistence.repository.OfficeTypeRepository;
import com.belpost.apas.service.common.LookupServiceImpl;

//@Service
public class OfficeTypeServiceImpl extends
    LookupServiceImpl<OfficeTypeModel, OfficeType> {

    public OfficeTypeServiceImpl(OfficeTypeRepository repository, OfficeTypeMapper mapper) {
        super(repository, mapper);
    }
}
