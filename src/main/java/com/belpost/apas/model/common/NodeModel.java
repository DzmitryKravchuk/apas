package com.belpost.apas.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "parentId", callSuper = true)
public abstract class NodeModel extends LookupModel{

    private Long parentId;
}
