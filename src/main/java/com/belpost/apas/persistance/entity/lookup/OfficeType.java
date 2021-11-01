package com.belpost.apas.persistance.entity.lookup;

import com.belpost.apas.persistance.entity.LookupEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("office_type")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
public class OfficeType {

  @Id
  private Long id;

  private String name;

  private String code;
}
