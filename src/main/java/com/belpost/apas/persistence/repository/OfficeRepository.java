package com.belpost.apas.persistence.repository;

import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.LookupRepository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;

import java.util.Optional;

public interface OfficeRepository extends LookupRepository<Office> {

    @Override
    @Query("SELECT office.id AS id, "
            + "office.name AS name, "
            + "office.code AS code, "
            + "office.parent_office_id AS parent_office_id, "
            + "officeType.id AS officetype_id, "
            + "officeType.name AS officetype_name, "
            + "officeType.code AS officetype_code "
            + "FROM \"office\" AS office "
            + "LEFT OUTER JOIN \"office_type\" officeType "
            + "ON officeType.id = office.office_type_id "
            + "WHERE office.code = :code")
    Optional<Office> findByCode(String code);

    @Query("SELECT office.id AS id, "
            + "office.name AS name, "
            + "office.code AS code, "
            + "office.parent_office_id AS parent_office_id, "
            + "officeType.id AS officetype_id, "
            + "officeType.name AS officetype_name, "
            + "officeType.code AS officetype_code "
            + "FROM \"office\" AS office "
            + "LEFT OUTER JOIN \"office_type\" officeType "
            + "ON officeType.id = office.office_type_id "
            + "WHERE office.parent_office_id IN (:parentIds)")
    List<Office> findAllByParentId(Long... parentIds);

    @Override
    @Query("SELECT office.id AS id, "
            + "office.name AS name, "
            + "office.code AS code, "
            + "office.parent_office_id AS parent_office_id, "
            + "officeType.id AS officetype_id, "
            + "officeType.name AS officetype_name, "
            + "officeType.code AS officetype_code "
            + "FROM \"office\" AS office "
            + "LEFT OUTER JOIN \"office_type\" officeType "
            + "ON officeType.id = office.office_type_id ")
    List<Office> findAll();
}
