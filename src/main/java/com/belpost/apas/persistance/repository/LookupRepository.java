package com.belpost.apas.persistance.repository;

import com.belpost.apas.persistance.entity.LookupEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LookupRepository <T extends LookupEntity> extends CrudRepository<T, Long> {

  Optional<T> findByCode(String code);
}
