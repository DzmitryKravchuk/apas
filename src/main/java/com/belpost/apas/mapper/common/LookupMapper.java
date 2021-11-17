package com.belpost.apas.mapper.common;

import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.persistance.entity.common.LookupEntity;

public interface LookupMapper <M extends LookupModel, E extends LookupEntity> {

    M mapToModel(E entity);

    E mapToEntity(M model);
}
