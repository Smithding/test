package com.tempus.gss.product.ift.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出Excel公共方法
 *
 * @author wangcp
 * @version 1.0
 */
public class ExportReportUtil<T> {
    public Logger log = LogManager.getLogger("IFTExportReportUtil");
    
    /**
     * 显示的导出表的标题
     */
    private String title;
    
    /**
     * 导出表的列名
     */
    private String[] rowName;
    /**
     * 表的每一行
     */
    private List<T> dataList = new ArrayList<>();
    /**
     * 列数
     */
    private int COL;
    
    /**
     * 构造方法，传入要导出的数据,子类继承这个类，重写构造方法；
     */
    public ExportReportUtil(String title, String[] rowName, List<T> dataList, int col) {
        this.rowName = rowName;
        this.title = title;
        this.dataList = dataList;
        this.COL = col;
    }
    
    public ExportReportUtil(String title, String[] rowName, List<T> dataList) {
        this.rowName = rowName;
        this.title = title;
        this.dataList = dataList;
    }
    
    /**
     * 导出数据
     */
    public HSSFWorkbook export() throws Exception {
        try {
            // 创建工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建工作表
            HSSFSheet sheet = workbook.createSheet(title);
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            //获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);
            HSSFCellStyle cellStyle = this.getStyle(workbook);
            //单元格样式对象
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
            HSSFDataFormat dataFormat = workbook.createDataFormat();
            
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);
            
            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);
            
            // 在索引2的位置创建行(最顶端的行开始的第二行)
            
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);
                
                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
                
                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                
                //设置列头单元格的值
                cellRowName.setCellValue(text);
                
                //设置列头单元格样式
                cellRowName.setCellStyle(columnTopStyle);
            }
            
            //将查询出的数据设置到sheet对应的单元格中
            setSheetValue(dataList, sheet, cellStyle, style, dataFormat);
            
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            return workbook;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    /**
     * 设置单元格的值
     * 需要被覆盖
     */
    public void setSheetValue(List<T> dataList, HSSFSheet sheet, HSSFCellStyle cellStyle, HSSFCellStyle style, DataFormat dataFormat) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/DD HH:mm");
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            T obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 3);
            //设置防止数字文本过长，导致单元格显示科学计数法
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
            //是否使用上面设置的style
            boolean setOlderStyle;
            for (int j = 0; j <= COL; j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j);
                setOlderStyle = true;
                
                switch (j) {
                    case 0: {
                        cell.setCellValue(i + 1);
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    default:
                        break;
                }
                
                //设置单元格样式
                if (!setOlderStyle) {
                    cell.setCellStyle(style);
                }
                
            }
        }
        HSSFRow row = sheet.createRow(dataList.size() + 3);
        int curRow = row.getRowNum();
        
        row.createCell(11).setCellType(CellType.BLANK);
        row.createCell(11).setCellFormula("SUM(L4:L" + curRow + ")");
        //强制使sheet执行公式
        sheet.setForceFormulaRecalculation(true);
        
    }
    
    /**
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        
        //在样式用应用设置的字体;
        style.setFont(font);
        
        //设置自动换行;
        style.setWrapText(false);
        
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        return style;
        
    }
    
    /**
     * 列数据信息单元格样式
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        
        // 设置字体
        HSSFFont font = workbook.createFont();
        
        //设置字体大小
        
        font.setFontHeightInPoints((short) 10);
        
        //字体加粗
        
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        //设置字体名字
        font.setFontName("Courier New");
        
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        
        //在样式用应用设置的字体;
        style.setFont(font);
        
        //设置自动换行;
        style.setWrapText(false);
        
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        return style;
        
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String[] getRowName() {
        return rowName;
    }
    
    public void setRowName(String[] rowName) {
        this.rowName = rowName;
    }
    
    public List<?> getDataList() {
        return dataList;
    }
    
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
    
    public int getCOL() {
        return COL;
    }
    
    public void setCOL(int COL) {
        this.COL = COL;
    }
    
}
