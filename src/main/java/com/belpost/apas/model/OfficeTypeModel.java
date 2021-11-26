package com.belpost.apas.model;

import com.belpost.apas.model.common.LookupModel;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class OfficeTypeModel extends LookupModel {

    public static final String WORK_PLACE_CODE = "WORK_PLACE";
    public static final String POST_OFFICE_CODE = "POST_OFFICE";
    public static final String LOCAL_OFFICE_CODE = "LOCAL_OFFICE";
    public static final String REGIONAL_OFFICE_CODE = "REGIONAL_OFFICE";
    public static final String ROOT_OFFICE_CODE = "ROOT_OFFICE";
}