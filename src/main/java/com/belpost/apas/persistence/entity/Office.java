package com.belpost.apas.persistence.entity;

import com.belpost.apas.persistence.entity.common.NodeEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Table("office")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "officeTypeId", callSuper = true)
public class Office extends NodeEntity {

    private Long officeTypeId;

}
