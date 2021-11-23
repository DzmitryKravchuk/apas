package com.belpost.apas.mapper;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistance.entity.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {OfficeTypeMapper.class})
public interface OfficeMapper extends LookupMapper<OfficeModel, Office> {

    @Mapping(target = "officeTypeModel", source = "entity.officeType")
    OfficeModel mapToModel(Office entity);

    @Mapping(target = "officeType", source = "model.officeTypeModel")
    Office mapToEntity (OfficeModel model);
}
