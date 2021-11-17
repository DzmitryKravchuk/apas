package com.belpost.apas.utils;

import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistance.entity.OfficeType;
import java.util.HashMap;
import java.util.Map;

public class OfficeTypeUtils {

    public static Map<String, OfficeType> entities;
    public static Map<String, OfficeTypeModel> models;
    private static long idCount;

    static {
        createEntities();
        createModels();
    }

    private static void createEntities() {
        idCount = 1L;
        entities = new HashMap<>();
        createOfficeType(OfficeTypeModel.WORK_PLACE_CODE, "ВРМ");
        createOfficeType(OfficeTypeModel.POST_OFFICE_CODE, "ОПС");
        createOfficeType(OfficeTypeModel.LOCAL_OFFICE_CODE, "РайУчасток");
        createOfficeType(OfficeTypeModel.REGIONAL_OFFICE_CODE, "REGIONAL_OFFICE");
        createOfficeType(OfficeTypeModel.ROOT_OFFICE_CODE, "ROOT_OFFICE");
        idCount = 1L;
    }

    private static void createModels() {
        idCount = 1L;
        models = new HashMap<>();
        createOfficeTypeModel(OfficeTypeModel.WORK_PLACE_CODE, "ВРМ");
        createOfficeTypeModel(OfficeTypeModel.POST_OFFICE_CODE, "ОПС");
        createOfficeTypeModel(OfficeTypeModel.LOCAL_OFFICE_CODE, "РайУчасток");
        createOfficeTypeModel(OfficeTypeModel.REGIONAL_OFFICE_CODE, "REGIONAL_OFFICE");
        createOfficeTypeModel(OfficeTypeModel.ROOT_OFFICE_CODE, "ROOT_OFFICE");
        idCount = 1L;
    }

    private static void createOfficeTypeModel(String officeCode, String officeName) {
        OfficeTypeModel model = new OfficeTypeModel();
        model.setId(idCount);
        model.setCode(officeCode);
        model.setName(officeName);
        models.put(officeCode, model);
        idCount++;
    }

    private static void createOfficeType(String officeCode, String officeName) {
        OfficeType entity = new OfficeType();
        entity.setId(idCount);
        entity.setCode(officeCode);
        entity.setName(officeName);
        entities.put(officeCode, entity);
        idCount++;
    }

    public static OfficeType getOfficeType(String code) {
        return entities.get(code);
    }

    public static OfficeTypeModel getOfficeTypeModel(String code) {
        return models.get(code);
    }

}
