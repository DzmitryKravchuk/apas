package com.belpost.apas.model;

import com.belpost.apas.model.common.LookupModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class OfficeModel extends LookupModel {

    private String officeTypeCode;

    private String parentOfficeCode;

}
