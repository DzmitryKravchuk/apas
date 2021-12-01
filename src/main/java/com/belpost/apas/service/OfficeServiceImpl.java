package com.belpost.apas.service;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import com.belpost.apas.service.common.LookupServiceImpl;
import java.io.File;
import java.util.List;

//@Service
public class OfficeServiceImpl extends
    LookupServiceImpl<OfficeModel, Office> implements OfficeService{

    public OfficeServiceImpl(LookupRepository<Office> repository,
        LookupMapper<OfficeModel, Office> mapper) {
        super(repository, mapper);
    }

    @Override
    public List<OfficeModel> readFile(File file) {
        return null;
    }
}
