package com.belpost.apas.persistance.entity.lookup;

import com.belpost.apas.persistance.entity.LookupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("office")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Office extends LookupEntity {

    @Id
    private Long id;

    private Long parentOfficeId;

    private OfficeType officeType;

}
