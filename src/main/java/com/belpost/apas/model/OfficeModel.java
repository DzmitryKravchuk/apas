package com.belpost.apas.model;

import com.belpost.apas.model.common.NodeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class OfficeModel extends NodeModel {

    private String officeTypeCode;

    private Integer hierarchyLvl;

    private String parentOfficeCode;

}
