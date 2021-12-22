package com.belpost.apas.persistence.repository;

import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.LookupRepository;
import java.util.List;

public interface OfficeRepository extends LookupRepository<Office> {

    List<Office> findAllByParentOfficeIdIn (Long... officeTypeIds);
}
