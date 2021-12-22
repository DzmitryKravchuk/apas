package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import com.belpost.apas.service.common.LookupService;
import com.belpost.apas.service.common.LookupServiceImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficeLookupServiceImpl extends
    LookupServiceImpl<OfficeModel, Office> implements LookupService<OfficeModel> {

    private final OfficeTypeLookupServiceImpl officeTypeService;
    private final OfficeMapper mapper;

    public OfficeLookupServiceImpl(LookupRepository<Office> repository,
                                   OfficeMapper mapper,
                                   OfficeTypeLookupServiceImpl officeTypeService) {
        super(repository);
        this.officeTypeService = officeTypeService;
        this.mapper = mapper;
    }

    @Override
    public OfficeModel getByCode(String code) {
        log.info("getByCode: {}", code);
        Office e = findByCode(code);

        return getOfficeModel(e);
    }

    private OfficeModel getOfficeModel(Office e) {
        String officeTypeCode = officeTypeService.getById(e.getOfficeTypeId()).getCode();
        String parentOfficeCode = findById(e.getParentOfficeId()).getCode();

        return mapper.mapToModel(e, officeTypeCode, parentOfficeCode);
    }

    @Override
    public OfficeModel getById(Long id) {
        return null;
    }

    @Override
    public List<OfficeModel> getAll() {
        return null;
    }

}
