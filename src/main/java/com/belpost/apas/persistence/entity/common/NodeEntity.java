package com.belpost.apas.persistence.entity.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "", callSuper = true)
public abstract class NodeEntity extends  LookupEntity{

    private Long parentId;

}
