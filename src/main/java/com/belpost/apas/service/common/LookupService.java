package com.belpost.apas.service.common;

import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.persistence.entity.common.LookupEntity;
import java.util.List;

public interface LookupService <M extends LookupModel, E extends LookupEntity> {

    M getByCode(String code);

    List<M> findAll();
}
