package com.belpost.apas.service;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistance.entity.Office;
import com.belpost.apas.persistance.repository.common.LookupRepository;
import com.belpost.apas.service.common.LookupServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImpl extends
    LookupServiceImpl<OfficeModel, Office> implements OfficeService{

    public OfficeServiceImpl(LookupRepository<Office> repository,
        LookupMapper<OfficeModel, Office> mapper) {
        super(repository, mapper);
    }
}
