package com.tempus.gss.product.ift.api.utils.reportExtends;

import com.tempus.gss.product.ift.api.entity.vo.ReportRefundVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import com.tempus.gss.product.ift.api.utils.ExportReportUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;

import java.text.SimpleDateFormat;
import java.util.List;

public class RefundReport extends ExportReportUtil<ReportRefundVo> {
    /**
     * 构造方法，传入要导出的数据,子类继承这个类，重写构造方法；
     *
     * @param title
     * @param rowName
     * @param dataList
     */
    public RefundReport(String title, String[] rowName, List<ReportRefundVo> dataList, int col) {
        super(title, rowName, dataList, col);
    }
    
    @Override
    public void setSheetValue(List<ReportRefundVo> dataList, HSSFSheet sheet, HSSFCellStyle cellStyle, HSSFCellStyle style, DataFormat dataFormat) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/DD HH:mm");
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            ReportRefundVo obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 3);
            //设置防止数字文本过长，导致单元格显示科学计数法
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
            //是否使用上面设置的style
            boolean setOlderStyle;
            for (int j = 0; j <= super.getCOL(); j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j);
                setOlderStyle = true;
                
                switch (j) {
                    case 0: {
                        //供应商
                        
                        cell.setCellValue(obj.getSupplierName());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 1: {
                        //种类
                        cell.setCellValue("退票");
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 2: {
                        //出票日期
                        cell.setCellValue(obj.getIssueTime());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 3: {
                        //销售单号
                        cell.setCellValue(obj.getSaleOrderNo().toString());
                        cell.setCellType(CellType.STRING);
                        cell.setCellStyle(cellStyle);
                        setOlderStyle = false;
                    }
                    break;
                    case 4: {
                        //票号
                        cell.setCellValue(obj.getTicketNo());
                        cell.setCellType(CellType.STRING);
                        cell.setCellStyle(cellStyle);
                        setOlderStyle = false;
                    }
                    break;
                    case 5: {
                        //航程
                        cell.setCellValue(obj.getLeg());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 6: {
                        //张数
                        cell.setCellValue(getTicketNum(obj.getTicketNo()));
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 7: {
                        //汇率
                        if (null != obj.getExchange()) {
                            cell.setCellValue(obj.getExchange().doubleValue());
                            cell.setCellType(CellType.STRING);
                        } else {
                            cell.setCellValue("--");
                            cell.setCellType(CellType.STRING);
                        }
                        
                    }
                    break;
                    case 8: {
                        //售价
                        if (null != obj.getSalePrice()) {
                            cell.setCellValue(obj.getSalePrice().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 9: {
                        //退票费
                        if (null != obj.getRefundPrice()) {
                            cell.setCellValue(obj.getRefundPrice().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 10: {
                        //实退款
                        if (null != obj.getFactRefundAccount()) {
                            cell.setCellValue(obj.getFactRefundAccount().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 11: {
                        //供应商退款
                        if (null != obj.getBuyRefundAccount()) {
                            cell.setCellValue(obj.getBuyRefundAccount().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 12: {
                        //税金
                        if (null != obj.getTax()) {
                            cell.setCellValue(obj.getTax().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 13: {
                        //结算净价
                        if (null != obj.getSettlePrice()) {
                            cell.setCellValue(obj.getSettlePrice().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    case 14: {
                        //到账情况
                        if (null != obj.getSettleStatus()) {
                            cell.setCellValue(obj.getSettleStatus());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 15: {
                        //冲抵营业毛利
                        if (null != obj.getChargeProfit()) {
                            cell.setCellValue(obj.getChargeProfit().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                    /*case 16: {
                        //毛利
                        if (null != obj.getGrossProfit()) {
                            cell.setCellValue(obj.getGrossProfit().doubleValue());
                            
                        } else {
                            cell.setCellValue(0);
                        }
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;*/
                    case 16: {
                        //出票员
                        cell.setCellValue(obj.getSaler());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 17: {
                        //退票员
                        cell.setCellValue(obj.getRefunder());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 18: {
                        //接单部门
                        cell.setCellValue(obj.getDep());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 19: {
                        //退票部门
                        cell.setCellValue(obj.getTicketDep());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 20: {
                        //客户名称
                        cell.setCellValue(obj.getCustomerName());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 21: {
                        //所属公司
                        cell.setCellValue(obj.getCustomerCompany());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 22: {
                        //结算方式
                        cell.setCellValue(obj.getSettleWay());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 23: {
                        //会员号
                        cell.setCellValue(obj.getCustomerNo());
                        cell.setCellType(CellType.STRING);
                        cell.setCellStyle(cellStyle);
                        setOlderStyle = false;
                    }
                    break;
                    case 24: {
                        //订单来源渠道
                        cell.setCellValue(obj.getSourceChannel());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 25: {
                        //退票原因
                        cell.setCellValue(obj.getReason());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    case 26: {
                        //退票备注
                        cell.setCellValue(obj.getRemark());
                        cell.setCellType(CellType.STRING);
                    }
                    break;
                    default:
                        break;
                }
                //设置单元格样式
                if (!setOlderStyle) {
                    //长文本时，使用另外的单元格样式，防止长文本折叠
                    cell.setCellStyle(style);
                }
                
            }
        }
        HSSFRow row = sheet.createRow(dataList.size() + 3);
        int curRow = row.getRowNum();
        
        //公式
        
        //张数
        
        row.createCell(6).setCellValue("合计");
        row.createCell(6).setCellType(CellType.STRING);
       
        //张数
        row.createCell(6).setCellType(CellType.BLANK);
        row.createCell(6).setCellFormula("SUM(G4:G" + curRow + ")");
        //退票费
        row.createCell(9).setCellType(CellType.BLANK);
        row.createCell(9).setCellFormula("SUM(J4:J" + curRow + ")");
        //实退款
        row.createCell(10).setCellType(CellType.BLANK);
        row.createCell(10).setCellFormula("SUM(K4:K" + curRow + ")");
        //供应商退款
        row.createCell(11).setCellType(CellType.BLANK);
        row.createCell(11).setCellFormula("SUM(L4:L" + curRow + ")");
        //结算净价
        row.createCell(13).setCellType(CellType.BLANK);
        row.createCell(13).setCellFormula("SUM(N4:N" + curRow + ")");
        //冲抵营业毛利
        row.createCell(15).setCellType(CellType.BLANK);
        row.createCell(15).setCellFormula("SUM(P4:P" + curRow + ")");
        //公式
        row.createCell(16).setCellType(CellType.BLANK);
        row.createCell(16).setCellFormula("SUM(Q4:Q" + curRow + ")");
        //强制使sheet执行公式
        sheet.setForceFormulaRecalculation(true);
    }
    
    private int getTicketNum(String ticketNos) {
        if (null != ticketNos && !"".equals(ticketNos)) {
            String[] split1 = ticketNos.split(",");
            if (split1.length <= 1) {
                //中文逗号
                String[] split2 = ticketNos.split("，");
                if (split2.length <= 1) {
                    return 1;
                } else {
                    return split2.length;
                }
            } else {
                return split1.length;
            }
        } else {
            return 0;
        }
    }
}
