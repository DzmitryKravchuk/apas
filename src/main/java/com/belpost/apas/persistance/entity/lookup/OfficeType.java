package com.belpost.apas.persistance.entity.lookup;

import com.belpost.apas.persistance.entity.LookupEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("office_type")
@Getter
@Setter
@RequiredArgsConstructor
public class OfficeType extends LookupEntity {

    @Id
    private Long id;

}
