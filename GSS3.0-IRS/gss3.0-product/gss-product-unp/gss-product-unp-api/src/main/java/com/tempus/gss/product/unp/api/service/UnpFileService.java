package com.tempus.gss.product.unp.api.service;

import com.tempus.gss.product.unp.api.entity.util.CellProperty;

import java.io.InputStream;
import java.util.Map;

public interface UnpFileService {
    
    Map<Integer, CellProperty> getCellProperties(String pathRelativeUnderClasspath);
    
    String dumpProperties(InputStream inp, String filePath) throws Exception;
}
