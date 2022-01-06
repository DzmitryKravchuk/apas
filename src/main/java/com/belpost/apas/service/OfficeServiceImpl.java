package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeRepository;
import com.belpost.apas.service.common.NodeServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class OfficeServiceImpl extends NodeServiceImpl<Office, OfficeModel> implements OfficeService{

    private final OfficeTypeService officeTypeService;

    public OfficeServiceImpl(OfficeRepository repository,
                             OfficeMapper mapper,
                             OfficeTypeService officeTypeService) {
        super(repository, mapper);
        this.officeTypeService = officeTypeService;
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeModel getByCode(String code) {
        log.info("getByCode: {}", code);
        OfficeModel m = super.getByCode(code);
        return getOfficeModel(m);
    }

    private OfficeModel getOfficeModel(OfficeModel model) {
        OfficeTypeModel type = officeTypeService.getById(model.getOfficeTypeId());
        String officeTypeCode = type.getCode();
        String parentOfficeCode = (model.getParentId()==null) ? null
            : super.getById(model.getParentId()).getCode();
        Integer hierarchyLvl = type.getHierarchyLvl();

        setRemainingFields(model, officeTypeCode, parentOfficeCode, hierarchyLvl);
        return model;
    }

    private void setRemainingFields(OfficeModel model, String officeTypeCode, String parentOfficeCode,
                                    Integer hierarchyLvl) {
        model.setOfficeTypeCode(officeTypeCode);
        model.setParentOfficeCode(parentOfficeCode);
        model.setHierarchyLvl(hierarchyLvl);
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeModel getById(Long id) {
        log.info("getById: {}", id);
        OfficeModel m = super.getById(id);
        return getOfficeModel(m);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfficeModel> getAll() {
        log.info("getAll");
        List<OfficeModel> ol = super.getAll();
        return getOfficeModels(ol);
    }

    private List<OfficeModel> getOfficeModels(List<OfficeModel> ol) {
        List<OfficeTypeModel> tl = officeTypeService.getAll();
        return ol.stream()
            .map(m -> processOtherFields(m, tl, ol))
            .collect(Collectors.toList());
    }

    private OfficeModel processOtherFields(OfficeModel e, List<OfficeTypeModel> tl, List<OfficeModel> ol) {
        setRemainingFields(e,
            getOfficeTypeCodes(tl).get(e.getOfficeTypeId()),
            getParentCodes(ol).get(e.getParentId()),
            getHierarchyLevels(tl).get(e.getOfficeTypeId())
        );

        return e;
    }

    private Map<Long, String> getParentCodes(List<OfficeModel> ol) {
        return ol.stream()
            .collect(Collectors.toMap(OfficeModel::getId, OfficeModel::getCode));
    }

    private Map<Long, Integer> getHierarchyLevels(List<OfficeTypeModel> tl) {
        return tl.stream()
            .collect(Collectors.toMap(OfficeTypeModel::getId, OfficeTypeModel::getHierarchyLvl));
    }

    private Map<Long, String> getOfficeTypeCodes(List<OfficeTypeModel> tl) {
        return tl.stream()
            .collect(Collectors.toMap(OfficeTypeModel::getId, OfficeTypeModel::getCode));
    }

    @Override
    @Transactional(readOnly = true)
    public Node<OfficeModel> getAsTree(Long ancestorId) {
        log.info("Get as tree for id: {}", ancestorId);
        Node<OfficeModel> root = super.getAsTree(ancestorId);
        List<OfficeTypeModel> tl = officeTypeService.getAll();
        List<OfficeModel> ol = super.convertToList(root);

        processOtherFields (root,
            getOfficeTypeCodes(tl),
            getHierarchyLevels(tl),
            getParentCodes(ol));

        return root;
    }

    public void processOtherFields (Node<OfficeModel> node,
                                    Map<Long, String> officeTypeCodes,
                                    Map<Long, Integer> hierarchyLevels,
                                    Map<Long, String> parentCodes) {

        setRemainingFields(node.getNodeElement(),
            officeTypeCodes.get(node.getNodeElement().getOfficeTypeId()),
            parentCodes.get(node.getNodeElement().getParentId()),
            hierarchyLevels.get(node.getNodeElement().getOfficeTypeId())
        );

        node.getChildren().forEach(each -> processOtherFields(each,
            officeTypeCodes,
            hierarchyLevels,
            parentCodes));
    }

}
