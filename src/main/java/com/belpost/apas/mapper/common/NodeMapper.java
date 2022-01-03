package com.belpost.apas.mapper.common;

import com.belpost.apas.model.common.NodeModel;
import com.belpost.apas.persistence.entity.common.NodeEntity;

public interface NodeMapper<M extends NodeModel, E extends NodeEntity> extends LookupMapper<M, E> {

    M mapToModel(E entity);

    E mapToEntity(M model);
}
