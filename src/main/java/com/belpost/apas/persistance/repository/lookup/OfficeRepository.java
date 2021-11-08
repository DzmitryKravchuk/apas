package com.belpost.apas.persistance.repository.lookup;

import com.belpost.apas.persistance.entity.lookup.Office;
import com.belpost.apas.persistance.repository.LookupRepository;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.Optional;

public interface OfficeRepository extends LookupRepository<Office> {

    @Override
    @Query("WITH RECURSIVE children "
        + "AS " +
        "("
        + "SELECT "
        + "office.id AS subOffice_id, "
        + "office.name AS subOffice_name, "
        + "office.code AS subOffice_code, "
        + "NULL::integer AS parent_id "
        + "FROM office "
        + "WHERE office.parent_office_id IS NULL "
        + "UNION "
        + "SELECT "
        + "so.id AS subOffice_id, "
        + "so.name AS subOffice_name, "
        + "so.code AS subOffice_code, "
        + "so.parent_office_id AS parent_id "
        + "FROM office so "
        + "JOIN children tree ON so.parent_office_id = tree.id" +
        ") "
        + "SELECT * FROM children "
        + "WHERE tree.code = :code")
    Optional<Office> findByCode(String code);

}
