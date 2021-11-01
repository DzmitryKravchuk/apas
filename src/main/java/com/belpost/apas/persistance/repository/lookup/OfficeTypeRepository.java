package com.belpost.apas.persistance.repository.lookup;

import com.belpost.apas.persistance.entity.lookup.OfficeType;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeTypeRepository extends CrudRepository<OfficeType, Long> {

  Optional<OfficeType> findByCode(String code);
}
