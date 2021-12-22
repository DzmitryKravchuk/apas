package com.belpost.apas.service.common;

import com.belpost.apas.model.common.LookupModel;
import java.util.List;

public interface LookupService <M extends LookupModel> {

    M getByCode(String code);

    M getById(Long id);

    List<M> getAll();
}
