package com.belpost.apas.mapper;

import com.belpost.apas.mapper.common.LookupMapper;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistance.entity.OfficeType;
import org.mapstruct.Mapper;

@Mapper
public interface OfficeTypeMapper extends LookupMapper<OfficeTypeModel, OfficeType> {

    OfficeTypeModel mapToModel(OfficeType entity);

    OfficeType mapToEntity (OfficeTypeModel model);
}
