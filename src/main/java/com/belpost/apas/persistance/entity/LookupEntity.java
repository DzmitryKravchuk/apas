package com.belpost.apas.persistance.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
public abstract class LookupEntity {

    private String name;

    private String code;
}
