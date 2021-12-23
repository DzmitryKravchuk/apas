package com.belpost.apas.service;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import com.belpost.apas.service.common.NodeServiceImpl;

public class OfficeNodeServiceImpl extends NodeServiceImpl {
    private  final  LookupMapper mapper;

    public OfficeNodeServiceImpl(LookupRepository repository, LookupMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }
}
