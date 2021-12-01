package com.belpost.apas.persistence.entity.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
public abstract class LookupEntity {
    @Column("name")
    protected String name;

    @Column("code")
    protected String code;
}
