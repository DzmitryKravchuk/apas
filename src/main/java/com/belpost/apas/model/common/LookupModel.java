package com.belpost.apas.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class LookupModel {

    private Long id;

    private String name;

    private String code;
}
