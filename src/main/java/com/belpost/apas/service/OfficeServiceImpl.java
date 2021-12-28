package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.NodeRepository;
import com.belpost.apas.service.common.LookupService;
import com.belpost.apas.service.common.NodeService;
import com.belpost.apas.service.common.NodeServiceImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class OfficeServiceImpl extends
    NodeServiceImpl<Office> implements LookupService<OfficeModel>, NodeService<OfficeModel> {

    private final OfficeTypeServiceImpl officeTypeService;
    private final OfficeMapper mapper;

    public OfficeServiceImpl(NodeRepository<Office> repository,
                             OfficeMapper mapper,
                             OfficeTypeServiceImpl officeTypeService) {
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
        String parentOfficeCode = findById(e.getParentId()).getCode();

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
        return getOfficeModels(ol);
    }

    private List<OfficeModel> getOfficeModels(List<Office> ol) {
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
            officeParentCodeMap.get(e.getParentId())
        );
    }

    @Override
    public Map<Integer, List<OfficeModel>> getChildrenGenerations(Long ancestorId, Integer marginalDescendantLevel) {
        Map<Integer, List<OfficeModel>> childrenGenerations = new HashMap<>();
        int i = marginalDescendantLevel;
        int genLevel = 1;
        List<Long> parentIds = Collections.singletonList(ancestorId);

        while (i > 0 && !findChildrenGenerations(parentIds.toArray(new Long[0])).isEmpty()) {
            List<Office> childrenGen = findChildrenGenerations(parentIds.toArray(new Long[0]));
            childrenGenerations.put(genLevel, getOfficeModels(childrenGen));

            parentIds = childrenGen.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());

            genLevel++;
            i--;
        }

        return childrenGenerations;
    }
}
