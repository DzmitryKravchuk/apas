package com.belpost.apas.model.lookup;

import com.belpost.apas.model.LookupModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class OfficeModel extends LookupModel {

    private OfficeTypeModel officeTypeModel;

    private Long parentOfficeId;
}
