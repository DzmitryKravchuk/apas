package com.belpost.apas.service.xlsx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class DataModel<T> {

    private final Map<String, String> metadata = new HashMap<>();
    private final List<T> content = new ArrayList<>();

}
