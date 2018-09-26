package com.tempus.gss.product.unp.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.unp.api.entity.util.CellProperty;
import com.tempus.gss.product.unp.api.service.UnpFileService;

@Service
public class UnpFileServiceImpl implements UnpFileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public Map<Integer, CellProperty> getCellProperties(String pathRelativeUnderClasspath) {
//        File file = null;
        Map<Integer, CellProperty> result = new HashMap<>();
        ArrayList<CellProperty> list;
        try {
//            file = ResourceUtils.getFile("classpath:resources/" + pathRelativeUnderClasspath + ".yml");
        	Resource resource = new ClassPathResource(pathRelativeUnderClasspath + ".yml");
            list = Yaml.loadType(resource.getInputStream(), ArrayList.class);
            list.forEach(o -> {
                if (o != null) {
                    result.put(o.getIndex(), o);
                }
            });
        } catch (Exception e) {
            logger.error("FileNotFoundException", e);
        }
        return result;
    }
    
    private String getCellValue(Cell cell) {
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }
    
    @Override
    public String dumpProperties(InputStream inp, String filePath) throws Exception {
        String path = "classpath:" + filePath + ".yml";
        File dumpFile = ResourceUtils.getFile(path);
        Map<Integer, CellProperty> propertyMap = new HashMap<>();
        Workbook wb = null;
        wb = WorkbookFactory.create(inp);
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(0);
            // 操作数据
            Iterator<Row> rowIterator = sheet.rowIterator();
            int curRow = 0;
            while (rowIterator.hasNext()) {
                curRow++;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int curCell = 0;
                while (cellIterator.hasNext()) {
                    curCell++;
                    Cell cell = cellIterator.next();
                    String value = getCellValue(cell);
                    CellProperty cellProperty = propertyMap.get(curCell);
                    if (cellProperty == null) {
                        cellProperty = new CellProperty();
                        propertyMap.put(curCell, cellProperty);
                    }
                    if (curRow == 1) {
                        //表头
                        cellProperty.setValue(value);
                    }
                    if (curRow == 2) {
                        //表头
                        cellProperty.setName(value);
                    }
                    if (curRow == 3) {
                        //表头
                        cellProperty.setConvert("true".equalsIgnoreCase(value));
                    }
                    propertyMap.replace(curCell, cellProperty);
                }
            }
            wb.close();
            List<CellProperty> list = new ArrayList<>();
            propertyMap.forEach((k, v) -> {
                v.setIndex(k);
                list.add(v);
            });
            Yaml.dump(list, dumpFile);
        }
        return path;
    }
}

