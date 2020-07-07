package com.sys.comm.util.excel;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 读取不规则表头excel工具类
 */
public class ReadExcelUtil {

    /** 2003- 版本的excel */
    private static String XLS = "xls";

    /** 2007+ 版本的excel */
    private static String XLSX = "xlsx";

    /** 格式化数字 */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#########");

    /** 格式化百分比格式，后面不足2位的用0补齐 */
    private static final DecimalFormat DECIMAL_FORMAT_PERCENT = new DecimalFormat("##.00");

     /** 格式化日期 */
    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

     /** 格式化科学计数器 */
    private static final DecimalFormat DECIMAL_FORMAT_NUMBER  = new DecimalFormat("0.00E000");

     /** 小数匹配 */
    private static final Pattern POINTS_PATTERN = Pattern.compile("0.0+_*[^/s]+");

    @Getter
    private Workbook wb;
    private Sheet sheet;
    private Row row;

    /**
     * 初始化workbook
     * @param filName
     * @param is
     */
    public ReadExcelUtil(String filName, InputStream is) {
        if (StringUtils.isBlank(filName)){
            return;
        }
        String ext = FilenameUtils.getExtension(filName);
        try {
            if(XLS.equals(ext)){
                wb = new HSSFWorkbook(is);
            }else if(XLSX.equals(ext)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关键字匹配excel表头并读取excel表头的内容
     * @return
     * @throws Exception
     */
    public Map<String, Object> readExcelTitle(String keyWord) throws Exception {
        if(null == wb){
            throw new Exception("Workbook对象为空！");
        }
        //默认取第一个sheet
        sheet = wb.getSheetAt(0);
        //根据关键字循环匹配是否是表头
        int lastRowNum = sheet.getLastRowNum();
        boolean matchedHeader = false;
        int headerCellNum = 0;
        loop : for (int i = 0; i < lastRowNum; i++) {
            row = sheet.getRow(i);
            if (null == row){
                continue ;
            }
            // 标题总列数
            int cellNum = row.getPhysicalNumberOfCells();
            for (int j = 0; j < cellNum; j++) {
                if (null == row.getCell(j)){
                    continue;
                }
                String stringCellValue = getCellFormatValue(row.getCell(j)).toString();
                if (StringUtils.isNotBlank(stringCellValue) && stringCellValue.contains(keyWord)){
                    matchedHeader = true;
                    headerCellNum = cellNum;
                    break loop;
                }
            }
        }
        if (!matchedHeader){
            return null;
        }
        Map<String, Object> map = Maps.newHashMap();
        String[] title = new String[headerCellNum];
        for (int k = 0; k < headerCellNum; k++) {
            title[k] = row.getCell(k).getStringCellValue();
        }
        map.put("header",title);
        map.put("headerRowNum",row.getRowNum());
        return map;
    }
    /**
     * 读取excel内容
     * @return
     * @throws Exception
     */
    public Map<Integer, Map<String, Object>> readExcelContent(int headerRowNum, String keyWord, int sheetNo) throws Exception {
        if(null == wb){
            throw new Exception("Workbook对象为空！");
        }
        Map<Integer, Map<String, Object>> content = Maps.newHashMap();
        sheet = wb.getSheetAt(sheetNo);
        int rowNum = sheet.getLastRowNum();
        Row headRow = sheet.getRow(headerRowNum);
        if (rowNum == 0 || null == headRow){
            return content;
        }
        int colNum = headRow.getPhysicalNumberOfCells();
        // 正文内容从表头下一行开始
        for (int i = headerRowNum + 1; i <= rowNum; i++) {
            this.row = sheet.getRow(i);
            if (null == this.row){
                continue;
            }
            int j = 0;
            Map<String, Object> result = Maps.newHashMap();
            while (j < colNum) {
                Object cellValue = getCellFormatValue(this.row.getCell(j));
                //获取表头标题
                Object headerValue = getCellFormatValue(sheet.getRow(headerRowNum).getCell(j));
                result.put((String) headerValue, cellValue);
                j++;
            }
            if (null != result.get(keyWord) && !"".equals(result.get(keyWord))) {
                content.put(i, result);
            }
        }
        return content;

    }
    /**
     * 根据cell类型取值
     * @param cell
     * @return
     */
    private Object getCellFormatValue(Cell cell) {
        Object value = null;
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case _NONE:
                    break;
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //日期 统一转成 yyyy-MM-dd
                        value = FAST_DATE_FORMAT.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                    } else if ("@".equals(cell.getCellStyle().getDataFormatString())
                            || "General".equals(cell.getCellStyle().getDataFormatString())
                            || "0_ ".equals(cell.getCellStyle().getDataFormatString())) {
                        //文本  or 常规 or 整型数值
                        value = DECIMAL_FORMAT.format(cell.getNumericCellValue());
                    } else if (POINTS_PATTERN.matcher(cell.getCellStyle().getDataFormatString()).matches()) {
                        //正则匹配小数类型
                        double cellValue = cell.getNumericCellValue();
                        DecimalFormat format = new DecimalFormat("0.00");
                        value = rvZeroAndDot(String.valueOf(format.format(cellValue)));
                    } else if ("0.00E+00".equals(cell.getCellStyle().getDataFormatString())) {
                        //科学计数
                        value = DECIMAL_FORMAT_NUMBER.format(cell.getNumericCellValue());
                    } else if ("0.00%".equals(cell.getCellStyle().getDataFormatString())) {
                        //百分比
//                        value = DECIMAL_FORMAT_PERCENT.format(cell.getNumericCellValue());
                        value = rvZeroAndDot(String.valueOf(cell.getNumericCellValue()));
                    } else if ("# ?/?".equals(cell.getCellStyle().getDataFormatString())) {
                        //分数
                        value = cell.getNumericCellValue();
                    } else {
                        //货币
                        value = DecimalFormat.getCurrencyInstance().format(cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case BLANK:
                    value = "";
                    break;
                default:
                    value = cell.toString();
            }
        }
        return value;
    }

    public static String rvZeroAndDot(String s){
        if (s.isEmpty()) {
            return null;
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}

