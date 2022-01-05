package com.belpost.apas.service;

import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.service.common.LookupService;
import java.util.List;

public interface OfficeTypeService extends LookupService<OfficeTypeModel> {

    OfficeTypeModel getByCode(String code);

    OfficeTypeModel getById(Long id);

    List<OfficeTypeModel> getAll();
}
