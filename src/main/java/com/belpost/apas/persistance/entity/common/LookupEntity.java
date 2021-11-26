package com.belpost.apas.persistance.entity.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
public abstract class LookupEntity {

    protected String name;

    protected String code;
}