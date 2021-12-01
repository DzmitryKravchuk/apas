package com.belpost.apas.service;

import com.belpost.apas.model.OfficeModel;
import java.io.File;
import java.util.List;

public interface OfficeService {

    public List<OfficeModel> readFile(File file);

}
