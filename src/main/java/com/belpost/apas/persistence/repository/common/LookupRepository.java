package com.belpost.apas.persistence.repository.common;

import com.belpost.apas.persistence.entity.common.LookupEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LookupRepository <T extends LookupEntity> extends CrudRepository<T, Long> {

  Optional<T> findByCode(String code);

  List<T> findAll();
}
