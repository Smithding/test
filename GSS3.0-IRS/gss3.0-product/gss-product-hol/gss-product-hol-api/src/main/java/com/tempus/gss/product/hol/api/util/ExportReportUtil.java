package com.tempus.gss.product.hol.api.util;

import com.tempus.gss.product.hol.api.entity.vo.ReportVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
public class ExportReportUtil {
    public Logger log = LogManager.getLogger("ExportReportUtil");
    
    /**
     * 显示的导出表的标题
     */
    private String title;
    
    /**
     * 导出表的列名
     */
    private String[] rowName;
    
    private List<ReportVO> dataList = new ArrayList<>();
    
    private static final int COL = 32;
    
    /**
     * 构造方法，传入要导出的数据,子类继承这个类，重写构造方法；
     */
    public ExportReportUtil(String title, String[] rowName, List<ReportVO> dataList) {
        this.rowName = rowName;
        this.title = title;
        this.dataList = dataList;
    }
    
    /**
     * 截取城市名称
     */
    public String getCity(String str) {
        if (null != str && !"".equals(str)) {
            String[] split1 = str.split("市");
            if (split1.length > 1) {
                return split1[0];
            } else {
                return str;
            }
        } else {
            return "";
        }
    }
    
    /**
     * 创建一张表
     *
     * @param sheet
     * @param style
     */
    public void setSheetValue(HSSFSheet sheet, HSSFCellStyle style, HSSFDataFormat dataFormat) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            ReportVO obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 3);
            //设置防止数字文本过长，导致单元格显示科学计数法
            style.setDataFormat(dataFormat.getFormat("@"));
            //是否使用上面设置的style
            for (int j = 0; j <= COL; j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j);
                switch (j) {
                    //序号
                    case 0: {
                        cell.setCellValue(i + 1);
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //订单号
                    case 1: {
                        if (null != obj.getSaleOrderNo()) {
                            cell.setCellValue(obj.getSaleOrderNo());
                            cell.setCellType(CellType.STRING);
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的单号为空");
                        }
                    }
                    break;
                    
                    //客户类型
                    case 2: {
                        if (null != obj.getCustomerTypeName()) {
                            cell.setCellValue(obj.getCustomerTypeName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的单号为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //客户卡号
                    case 3: {
                        if (null != obj.getCustomerNo()) {
                            cell.setCellValue(obj.getCustomerNo().toString());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的客户卡号为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    //客户名称
                    case 4: {
                        if (null != obj.getCustomerName()) {
                            cell.setCellValue(obj.getCustomerName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的客户名称为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //预订日期
                    case 5: {
                        if (null != obj.getCreateTime()) {
                            cell.setCellValue(sf.format(obj.getCreateTime()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预订日期为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //城市
                    case 6: {
                        if (null != obj.getHolAddr()) {
                            cell.setCellValue(getCity(obj.getHolAddr()));
                            cell.setCellType(CellType.STRING);
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的城市为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //酒店名称
                    case 7: {
                        if (null != obj.getHotelName()) {
                            cell.setCellValue(obj.getHotelName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的酒店名称为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //预计入住人
                    case 8: {
                        if (null != obj.getGuestName()) {
                            cell.setCellValue(obj.getGuestName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预计入住人为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //预计入住日期
                    case 9: {
                        if (null != obj.getArrivalDate()) {
                            cell.setCellValue(sf.format(obj.getArrivalDate()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预计入住日期为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //预计离店日期
                    case 10: {
                        if (null != obj.getDepartureDate()) {
                            cell.setCellValue(sf.format(obj.getDepartureDate()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预计离店日期为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //预定房间数
                    case 11: {
                        if (null != obj.getBookPro()) {
                            cell.setCellValue(obj.getBookPro());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预定房间数为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //预定间夜数
                    case 12: {
                        if (null != obj.getProNight()) {
                            cell.setCellValue(obj.getProNight());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预定间夜数为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //预定总价
                    case 13: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getTotalPrice().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预定总价为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //预定佣金率
                    case 14: {
                        if (null != obj.getRate()) {
                            cell.setCellValue(obj.getRate().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预定佣金率为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //预定佣金
                    case 15: {
                        if (null != obj.getSaleRefund()) {
                            cell.setCellValue(obj.getSaleRefund().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的预定佣金为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //上游状态
                    case 24: {
                        if (null != obj.getCheckStatusName()) {
                            cell.setCellValue(obj.getCheckStatusName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的上游状态为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    //下游状态
                    case 25: {
                        if (null != obj.getOrderStatus()) {
                            cell.setCellValue(obj.getOrderStatusName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的下游状态为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    //入住人
                    case 16: {
                        if (null != obj.getFactGuestName()) {
                            cell.setCellValue(obj.getFactGuestName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的入住人为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    //实际入住日期
                    case 17: {
                        if (null != obj.getFactArrivalDate()) {
                            cell.setCellValue(sf.format(obj.getFactArrivalDate()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际入住日期为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    //实际离店日期
                    case 18: {
                        if (null != obj.getFactDepartureDate()) {
                            cell.setCellValue(sf.format(obj.getFactDepartureDate()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际离店日期为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    //实际房间数
                    case 19: {
                        if (null == obj.getFactBookPro()) {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际房间数为空");
                            
                        } else {
                            cell.setCellValue(obj.getFactBookPro());
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //实际间夜数
                    case 20: {
                        if (null == obj.getFactProNight()) {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际间夜数为空");
                            
                        } else {
                            cell.setCellValue(obj.getFactProNight());
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //实际总价
                    case 21: {
                        if (null != obj.getFactTotalPrice()) {
                            cell.setCellValue(obj.getFactTotalPrice().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际总价为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //实际佣金率
                    case 22: {
                        if (null != obj.getFactRate()) {
                            cell.setCellValue(obj.getFactRate().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际佣金为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //实际佣金
                    case 23: {
                        if (null != obj.getFactSaleRefund()) {
                            cell.setCellValue(obj.getFactSaleRefund().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的实际佣金率为空");
                            
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //订房员
                    case 26: {
                        if (null != obj.getCreator()) {
                            cell.setCellValue(obj.getCreator());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的订房员为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //银行名称
                    case 27: {
                        if (null != obj.getBankName()) {
                            cell.setCellValue(obj.getBankName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的银行名称为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //银行卡号
                    case 28: {
                        if (null != obj.getCardNo()) {
                            cell.setCellValue(obj.getCardNo());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的银行卡号为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //开户名称
                    case 29: {
                        if (null != obj.getBankName()) {
                            cell.setCellValue(obj.getUserName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的开户名称为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //开户省份
                    case 30: {
                        if (null != obj.getProvince()) {
                            cell.setCellValue(obj.getProvince());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的开户省份为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //开户城市
                    case 31: {
                        if (null != obj.getBankCity()) {
                            cell.setCellValue(getCity(obj.getBankCity()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的开户城市为空");
                            
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //开户地址
                    case 32: {
                        if (null != obj.getBankAddr()) {
                            cell.setCellValue(obj.getBankAddr());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的开户地址为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    default:
                        break;
                }
                //设置单元格样式
                cell.setCellStyle(style);
            }
        }
        HSSFRow row = sheet.createRow(dataList.size() + 3);
        style.setFillBackgroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        int curRow = row.getRowNum();
        row.createCell(0).setCellType(CellType.STRING);
        for (int cellI = 1; cellI <= 32; cellI++) {
            row.createCell(cellI).setCellType(CellType.BLANK);
            row.getCell(cellI).setCellStyle(style);
        }
        row.getCell(0).setCellValue("合计");
        row.getCell(11).setCellFormula("SUM(L4:L" + curRow + ")");
        row.getCell(12).setCellFormula("SUM(M4:M" + curRow + ")");
        row.getCell(13).setCellFormula("SUM(N4:N" + curRow + ")");
        row.getCell(15).setCellFormula("SUM(P4:P" + curRow + ")");
        row.getCell(19).setCellFormula("SUM(T4:T" + curRow + ")");
        row.getCell(20).setCellFormula("SUM(U4:U" + curRow + ")");
        row.getCell(21).setCellFormula("SUM(V4:V" + curRow + ")");
        row.getCell(23).setCellFormula("SUM(X4:X" + curRow + ")");
        //强制使sheet执行公式
        sheet.setForceFormulaRecalculation(true);
        
    }
    
    public void setSheetValue2(HSSFSheet sheet, HSSFCellStyle style, HSSFDataFormat dataFormat) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/DD HH:mm");
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            ReportVO obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 3);
            //设置防止数字文本过长，导致单元格显示科学计数法
            style.setDataFormat(dataFormat.getFormat("@"));
            for (int j = 0; j <= COL; j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j);
                switch (j) {
                    //序号
                    case 0: {
                        cell.setCellValue(i + 1);
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //客户名称
                    case 1: {
                        if (null != obj.getCustomerName()) {
                            cell.setCellValue(obj.getCustomerName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的客户名称为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //客户类型
                    case 2: {
                        if (null != obj.getCustomerTypeName()) {
                            cell.setCellValue(obj.getCustomerTypeName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的客户类型为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //订单数
                    case 3: {
                        if (null != obj.getOrderCount()) {
                            cell.setCellValue(obj.getOrderCount().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的订单数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                        
                    }
                    break;
                    //房间数
                    case 4: {
                        if (null != obj.getBookPro()) {
                            cell.setCellValue(obj.getBookPro().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的房间数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //间夜数
                    case 5: {
                        if (null != obj.getProNight()) {
                            cell.setCellValue(obj.getProNight().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的间夜数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //总价
                    case 6: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getTotalPrice().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的总价为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //毛利
                    case 7: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getgProfit().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的毛利为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //净利
                    case 8: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getProfit().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的净利为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    default:
                        break;
                }
                cell.setCellStyle(style);
            }
        }
        HSSFRow row = sheet.createRow(dataList.size() + 3);
        style.setFillBackgroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        int curRow = row.getRowNum();
        row.createCell(0).setCellType(CellType.STRING);
        for (int cellI = 1; cellI <= 8; cellI++) {
            row.createCell(cellI).setCellType(CellType.BLANK);
            row.getCell(cellI).setCellStyle(style);
        }
        row.getCell(0).setCellValue("合计");
        row.getCell(4).setCellFormula("SUM(E4:E" + curRow + ")");
        row.getCell(5).setCellFormula("SUM(F4:F" + curRow + ")");
        row.getCell(6).setCellFormula("SUM(G4:G" + curRow + ")");
        row.getCell(7).setCellFormula("SUM(H4:H" + curRow + ")");
        row.getCell(8).setCellFormula("SUM(I4:I" + curRow + ")");
        //强制使sheet执行公式
        sheet.setForceFormulaRecalculation(true);
    }
    
    public void setSheetValue3(HSSFSheet sheet, HSSFCellStyle style, HSSFDataFormat dataFormat) {
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            ReportVO obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 3);
            //设置防止数字文本过长，导致单元格显示科学计数法
            style.setDataFormat(dataFormat.getFormat("@"));
            for (int j = 0; j <= COL; j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j);
                switch (j) {
                    //序号
                    case 0: {
                        cell.setCellValue(i + 1);
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //酒店名称
                    case 1: {
                        if (null != obj.getHotelName()) {
                            cell.setCellValue(obj.getHotelName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的酒店名称为空");
                        }
                        cell.setCellType(CellType.STRING);
                        
                    }
                    break;
                    
                    //城市
                    case 2: {
                        if (null != obj.getHolAddr()) {
                            cell.setCellValue(getCity(obj.getHolAddr()));
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的城市为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //订单数
                    case 3: {
                        if (null != obj.getOrderCount()) {
                            cell.setCellValue(obj.getOrderCount().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的订单数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                        
                    }
                    break;
                    //房间数
                    case 4: {
                        if (null != obj.getBookPro()) {
                            cell.setCellValue(obj.getBookPro().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的房间数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //间夜数
                    case 5: {
                        if (null != obj.getProNight()) {
                            cell.setCellValue(obj.getProNight().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的间夜数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //总价
                    case 6: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getTotalPrice().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的总价为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //毛利
                    case 7: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getgProfit().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的毛利为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //净利
                    case 8: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getProfit().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的净利为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    default:
                        break;
                }
                cell.setCellStyle(style);
            }
        }
        HSSFRow row = sheet.createRow(dataList.size() + 3);
        style.setFillBackgroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        int curRow = row.getRowNum();
        row.createCell(0).setCellType(CellType.STRING);
        for (int cellI = 1; cellI <= 8; cellI++) {
            row.createCell(cellI).setCellType(CellType.BLANK);
            row.getCell(cellI).setCellStyle(style);
        }
        row.getCell(0).setCellValue("合计");
        row.getCell(4).setCellFormula("SUM(E4:E" + curRow + ")");
        row.getCell(5).setCellFormula("SUM(F4:F" + curRow + ")");
        row.getCell(6).setCellFormula("SUM(G4:G" + curRow + ")");
        row.getCell(7).setCellFormula("SUM(H4:H" + curRow + ")");
        row.getCell(8).setCellFormula("SUM(I4:I" + curRow + ")");
        //强制使sheet执行公式
        sheet.setForceFormulaRecalculation(true);
    }
    
    public void setSheetValue4(HSSFSheet sheet, HSSFCellStyle style, HSSFDataFormat dataFormat) {
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            ReportVO obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 3);
            //设置防止数字文本过长，导致单元格显示科学计数法
            style.setDataFormat(dataFormat.getFormat("@"));
            //是否使用上面设置的style
            for (int j = 0; j <= COL; j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j);
                switch (j) {
                    //序号
                    case 0: {
                        cell.setCellValue(i + 1);
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //下一级编号
                    case 1: {
                        if (null != obj.getCustomerNo()) {
                            cell.setCellValue(obj.getCustomerNo());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的下一级编号为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //下一级名称
                    case 2: {
                        if (null != obj.getCustomerName()) {
                            cell.setCellValue(obj.getCustomerName());
                        } else {
                            cell.setCellValue("--");
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的下一级名称为空");
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    
                    //订单数
                    case 3: {
                        if (null != obj.getOrderCount()) {
                            cell.setCellValue(obj.getOrderCount().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的订单数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    //房间数
                    case 4: {
                        if (null != obj.getBookPro()) {
                            cell.setCellValue(obj.getBookPro().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的房间数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //间夜数
                    case 5: {
                        if (null != obj.getProNight()) {
                            cell.setCellValue(obj.getProNight().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的间夜数为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //总价
                    case 6: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getTotalPrice().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的总价为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //毛利
                    case 7: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getgProfit().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的毛利为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    //净利
                    case 8: {
                        if (null != obj.getTotalPrice()) {
                            cell.setCellValue(obj.getProfit().doubleValue());
                        } else {
                            cell.setCellValue(0);
                            log.info("报表导出模块,0907,第" + (i + 1) + "行的净利为空");
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    
                    default:
                        break;
                }
                cell.setCellStyle(style);
            }
        }
        HSSFRow row = sheet.createRow(dataList.size() + 3);
        style.setFillBackgroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        int curRow = row.getRowNum();
        row.createCell(0).setCellType(CellType.STRING);
        for (int cellI = 1; cellI <= 8; cellI++) {
            row.createCell(cellI).setCellType(CellType.BLANK);
            row.getCell(cellI).setCellStyle(style);
        }
        row.getCell(0).setCellValue("合计");
        row.getCell(4).setCellFormula("SUM(E4:E" + curRow + ")");
        row.getCell(5).setCellFormula("SUM(F4:F" + curRow + ")");
        row.getCell(6).setCellFormula("SUM(G4:G" + curRow + ")");
        row.getCell(7).setCellFormula("SUM(H4:H" + curRow + ")");
        row.getCell(8).setCellFormula("SUM(I4:I" + curRow + ")");
        //强制使sheet执行公式
        sheet.setForceFormulaRecalculation(true);
        
    }
    
    /**
     * 导出数据
     */
    public HSSFWorkbook export(Integer type) throws Exception {
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
            
            // 在索引2的位置创建行(最顶端的行开始的第二行)
            HSSFRow rowRowName = sheet.createRow(2);
            
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
            switch (type) {
                //将查询出的数据设置到sheet对应的单元格中
                case 1:
                default: {
                    setSheetValue(sheet, style, dataFormat);
                }
                break;
                case 2: {
                    setSheetValue2(sheet, style, dataFormat);
                }
                break;
                case 3: {
                    setSheetValue3(sheet, style, dataFormat);
                    
                }
                break;
                case 4: {
                    setSheetValue4(sheet, style, dataFormat);
                }
                break;
            }
            
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
     * 单元格样式
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        font.setFontName("Courier New");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.DASHED);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setFont(font);
        style.setWrapText(false);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
    
}
