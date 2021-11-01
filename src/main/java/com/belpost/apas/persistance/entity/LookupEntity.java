package com.belpost.apas.persistance.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
public abstract class LookupEntity {

    @Id
    private Long id;

    private String name;

    private String code;
}
