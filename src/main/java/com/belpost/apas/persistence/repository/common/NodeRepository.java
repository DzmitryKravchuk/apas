package com.belpost.apas.persistence.repository.common;

import com.belpost.apas.persistence.entity.Office;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NodeRepository <N> extends CrudRepository<N, Long> {

    List<N> findAllByParentOfficeIdIn (Long... parentOfficeIds);

}
