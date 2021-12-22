package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import com.belpost.apas.service.common.LookupService;
import com.belpost.apas.service.common.LookupServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        log.info("getById: {}", id);
        Office e = findById(id);
        return getOfficeModel(e);
    }

    @Override
    public List<OfficeModel> getAll() {
        log.info("getAll()");
        List<Office> ol = findAll();
        List<OfficeTypeModel> tl = officeTypeService.getAll();
        return ol.stream()
            .map(e -> convertFindCodes(e, ol, tl))
            .collect(Collectors.toList());
    }

    private OfficeModel convertFindCodes(Office e, List<Office> ol, List<OfficeTypeModel> tl) {
        Map<Long, String> officeTypeCodeMap = tl.stream()
            .collect(Collectors.toMap(OfficeTypeModel::getId, OfficeTypeModel::getCode));

        Map<Long, String> officeParentCodeMap = ol.stream()
            .collect(Collectors.toMap(Office::getId, Office::getCode));

        return mapper.mapToModel(e,
            officeTypeCodeMap.get(e.getOfficeTypeId()),
            officeParentCodeMap.get(e.getParentOfficeId())
        );
    }

}
