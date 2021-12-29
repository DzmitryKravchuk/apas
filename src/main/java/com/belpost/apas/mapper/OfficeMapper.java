package com.belpost.apas.mapper;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistence.entity.Office;
import org.mapstruct.Mapper;

@Mapper
public interface OfficeMapper {

    OfficeModel mapToModel(Office entity, String officeTypeCode, String parentOfficeCode, Integer hierarchyLvl);

    Office mapToEntity (OfficeModel model, Long officeTypeId);
}
