package com.belpost.apas.persistence.entity.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class LookupEntity {
    @Id
    protected Long id;

    protected String name;

    protected String code;
}
