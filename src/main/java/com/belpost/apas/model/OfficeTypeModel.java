package com.belpost.apas.model;

import com.belpost.apas.model.common.LookupModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "hierarchyLvl", callSuper = true)
public class OfficeTypeModel extends LookupModel {

    public static final String WORK_PLACE_CODE = "ВРМ";
    public static final String POST_OFFICE_CODE = "ОПС";
    public static final String LOCAL_OFFICE_CODE = "РайУч";
    public static final String REGIONAL_OFFICE_CODE = "РегУПС";
    public static final String ROOT_OFFICE_CODE = "Филиал";

    private Integer hierarchyLvl;
}
