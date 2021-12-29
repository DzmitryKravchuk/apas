package com.belpost.apas.persistence.entity;

import com.belpost.apas.persistence.entity.common.LookupEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Table("office_type")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "hierarchyLvl", callSuper = true)
public class OfficeType extends LookupEntity {

    private Integer hierarchyLvl;

}
