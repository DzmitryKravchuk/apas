package com.belpost.apas.persistence.repository.common;

import com.belpost.apas.persistence.entity.common.NodeEntity;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NodeRepository <N extends NodeEntity> extends LookupRepository<N> {

    List<N> findAllByParentIdIn (Long... parentOfficeIds);

}
