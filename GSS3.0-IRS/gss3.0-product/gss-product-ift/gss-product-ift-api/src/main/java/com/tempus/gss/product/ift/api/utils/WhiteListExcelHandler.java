//package com.tempus.gss.product.ift.api.utils;
//
//import com.tempus.gss.dps.dict.AgeType;
//import com.tempus.gss.dps.dict.CardType;
//import com.tempus.gss.dps.setting.entity.Whitelist;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <pre>
// * <b>描述信息</b>
// * <b>Description:白名单Excel处理</b>
// *
// * <b>Author:</b> Luyongjia
// * <b>Date:</b> 2016年12月21日  4:44 PM
// * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
// * <b>Changelog:</b>
// *   Ver   Date                             Author                Detail
// *   ----------------------------------------------------------------------
// *   0.1   2016年12月21日  4:44 PM   Luyongjia
// *         new file.
// * </pre>
// */
//public class WhiteListExcelHandler {
//
//    protected static final String[] titles = {"乘客姓名", "乘客类型", "证件类型", "证件号码", "联系电话"};
//
//    /**
//     * 07版本之前excel处理模式
//     *
//     * @param data 从文件服务器下载到的数据
//     * @return
//     */
//    public static WhiteListExcelResult parseWhiteList(byte[] data) {
//        InputStream inputStream = new ByteArrayInputStream(data);
//        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream)) {
//            HSSFSheet sheet = hssfWorkbook.getSheetAt(1);
//            return handSheet(sheet);
//        } catch (Exception e) {
//            return parseWhiteListOver2007(data);
//        }
//    }
//
//    /**
//     * 07版本的excel处理模式
//     *
//     * @param data 从文件服务器下载到的数据
//     * @return
//     */
//    private static WhiteListExcelResult parseWhiteListOver2007(byte[] data) {
//        InputStream inputStream = new ByteArrayInputStream(data);
//        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream)) {
//            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
//            return handSheet(sheet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new WhiteListExcelResult(data);
//    }
//
//    /**
//     * @param sheet 传入工作薄
//     * @return 处理结果
//     */
//    private static WhiteListExcelResult handSheet(Sheet sheet) {
//        boolean flag = true;
//        List<Whitelist> whitelists = new ArrayList<>();
//        int size = sheet.getPhysicalNumberOfRows();
//        for (int i = 1; i < size; i++) {
//            Row row = sheet.getRow(i);
//            //校验数据，如果返回false，则最终返回结果不包含白名单列表，而是矫正后的excel字节数组
//            boolean valid = validRow(row);
//            //如果出现校验失败，并且标志为true，则置为false
//            if (flag && !valid) {
//                flag = false;
//            }
//            //如果没有出现错误数据，则添加到列表
//            if (flag) {
//                Whitelist whitelist = handRow(row);
//                whitelists.add(whitelist);
//            }
//        }
//
//        if (flag) {
//            return new WhiteListExcelResult(whitelists);
//        } else {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            try {
//                sheet.getWorkbook().write(byteArrayOutputStream);
//                return new WhiteListExcelResult(byteArrayOutputStream.toByteArray());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 处理一行
//     */
//
//    private static Whitelist handRow(Row row) {
//        try {
//            String name = parseCell(row.getCell(0));
//            String ageType = parseCell(row.getCell(1));
//            String cardType = parseCell(row.getCell(2));
//            String cardNo = parseCell(row.getCell(3));
//            String phone = parseCell(row.getCell(4));
//            String cardRemark = parseCell(row.getCell(5));
//            Whitelist whitelist = new Whitelist();
//            whitelist.setName(name);
//            whitelist.setAgeType(ageType);
//            whitelist.setCardType(cardType);
//            whitelist.setCardNo(cardNo);
//            whitelist.setPhone(phone);
//            whitelist.setCardRemark(cardRemark);
//            return whitelist;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 校验行
//     *
//     * @param row 数据行
//     * @return false 校验未通过，需要返还给用户修改
//     */
//    private static boolean validRow(Row row) {
//        boolean flag = true;
//        Cell AgeTypeCell = row.getCell(1);
//        try {
//            String ageType = parseCell(AgeTypeCell);
//            AgeType.valueOf(ageType);
//        } catch (Exception e) {
//            flag = false;
//            //如果枚举转换发生异常，提示该单元格数据异常
//            AgeTypeCell.setCellValue("乘客类型异常");
//        }
//        Cell CardTypeCell = row.getCell(2);
//        try {
//            String cardType = parseCell(CardTypeCell);
//            CardType.valueOf(cardType);
//        } catch (Exception e) {
//            flag = false;
//            //如果枚举转换发生异常，提示该单元格数据异常
//            CardTypeCell.setCellValue("证件类型异常");
//        }
//        return flag;
//    }
//
//    /**
//     * 获取单元格数据
//     */
//    public static String parseCell(Cell cell) {
//        CellType cellTypeEnum = cell.getCellTypeEnum();
//        switch (cellTypeEnum) {
//            case _NONE:
//                break;
//            case NUMERIC:
//                //Excel中不包含浮点数，所以不处理
//                return String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue()).trim();
//            case STRING:
//                return String.valueOf(cell.getStringCellValue()).trim();
//            case FORMULA:
//                break;
//            case BLANK:
//                break;
//            case BOOLEAN:
//                break;
//            case ERROR:
//                break;
//        }
//        return null;
//    }
//
////    public static void main(String[] args) throws Exception {
////        File file = new File("/home/luyongjia/Desktop/WhiteListTemplate.xlsx");
////        byte[] data = FileUtils.readFileToByteArray(file);
////        WhiteListExcelResult whiteListExcelResult = parseWhiteList(data);
////
////        if (whiteListExcelResult.isValid())
////            System.out.println(whiteListExcelResult.getWhitelists());
////        else
////            FileUtils.writeByteArrayToFile(new File("/home/luyongjia/Desktop/WhiteListTemplate-valid.xlsx"), whiteListExcelResult.getAfterValid());
////    }
//
//    public static class WhiteListExcelResult {
//        private boolean valid;
//        private List<Whitelist> whitelists;
//        private byte[] afterValid;
//
//        WhiteListExcelResult(List<Whitelist> whitelists) {
//            this.whitelists = whitelists;
//            valid = true;
//        }
//
//        WhiteListExcelResult(byte[] afterValid) {
//            this.afterValid = afterValid;
//            valid = false;
//        }
//
//        public boolean isValid() {
//            return valid;
//        }
//
//        public List<Whitelist> getWhitelists() {
//            return whitelists;
//        }
//
//        public byte[] getAfterValid() {
//            return afterValid;
//        }
//    }
//
//}
