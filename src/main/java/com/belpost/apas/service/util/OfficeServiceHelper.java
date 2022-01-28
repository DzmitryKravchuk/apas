package com.belpost.apas.service.util;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.service.OfficeService;
import com.belpost.apas.service.OfficeTypeService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OfficeServiceHelper {

    private final Node<OfficeModel> node;
    private final OfficeService officeService;
    private final Map<Long, String> officeTypeCodes;
    private final Map<Long, Integer> hierarchyLevels;
    private final Map<Long, String> parentCodes;

    public OfficeServiceHelper(Node<OfficeModel> node,
                               OfficeService officeService,
                               OfficeTypeService officeTypeService) {
        this.node = node;
        this.officeService = officeService;

        List<OfficeTypeModel> tl = officeTypeService.getAll();
        this.officeTypeCodes = getOfficeTypeCodes(tl);
        this.hierarchyLevels = getHierarchyLevels(tl);

        this.parentCodes = getParentCodes(node);
    }

    public void setRemainingFields() {
        setRemainingFields(node);
    }

    private void setRemainingFields(Node<OfficeModel> nextNode) {
        doSetRemainingFields(nextNode.getNodeElement(),
            officeTypeCodes.get(nextNode.getNodeElement().getOfficeTypeId()),
            parentCodes.get(nextNode.getNodeElement().getParentId()),
            hierarchyLevels.get(nextNode.getNodeElement().getOfficeTypeId())
        );

        nextNode.getChildren().forEach(each -> setRemainingFields(each));
    }


    private void doSetRemainingFields(OfficeModel model, String officeTypeCode, String parentOfficeCode,
                                      Integer hierarchyLvl) {
        model.setOfficeTypeCode(officeTypeCode);
        model.setParentOfficeCode(parentOfficeCode);
        model.setHierarchyLvl(hierarchyLvl);
    }

    private Map<Long, String> getParentCodes(Node<OfficeModel> node) {
        List<OfficeModel> ol = officeService.convertToList(node);
        // add parent for root node
        if (node.getNodeElement().getParentId() != null) {
            ol.add(officeService.getParentById(node.getNodeElement().getParentId()));
        }
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
}
