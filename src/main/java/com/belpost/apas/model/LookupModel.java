package com.belpost.apas.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class LookupModel {

    private Long id;

    private String name;

    private String code;
}
