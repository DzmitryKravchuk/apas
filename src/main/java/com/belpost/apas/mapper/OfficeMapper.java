package com.belpost.apas.mapper;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OfficeMapper extends LookupMapper<OfficeModel, Office> {

    @Mapping(target = "parentOfficeCode", source = "parent.code")
    @Mapping(target = "officeTypeCode", source = "entity.officeType.code")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "code", source = "entity.code")
    OfficeModel mapToModel(Office entity, Office parent);

    @Mapping(target = "parentOfficeId", source = "parent.id")
    @Mapping(target = "officeType", source = "officeTypeModel")
    @Mapping(target = "code", source = "model.code")
    @Mapping(target = "name", source = "model.name")
    @Mapping(target = "id", source = "model.id")
    Office mapToEntity (OfficeModel model, OfficeModel parent, OfficeTypeModel officeTypeModel);
}
