package com.belpost.apas.persistance.repository.lookup;

import com.belpost.apas.persistance.entity.lookup.Office;
import com.belpost.apas.persistance.repository.LookupRepository;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.Optional;

public interface OfficeRepository extends LookupRepository<Office> {

    @Override
    @Query("SELECT office.id AS id, office.name AS name, office.code AS code, office.office_type_id AS office_type_id, office.parent_office_id AS parent_office_id, officeType.id AS officetype_id, officeType.name AS officetype_name, officeType.code AS officetype_code " +
            "FROM office " +
            "LEFT OUTER JOIN office_type officeType " +
            "ON officeType.id = office.office_type_id " +
            "WHERE office.code = ?")
    Optional<Office> findByCode(String code);

}
