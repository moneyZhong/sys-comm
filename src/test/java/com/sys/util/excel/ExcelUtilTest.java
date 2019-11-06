package com.sys.util.excel;


import com.google.gson.Gson;
import com.sys.comm.util.excel.ExcelUtil;
import com.sys.comm.util.excel.ReadExcelUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class ExcelUtilTest {

    @Test
    public void readRandomExcel() throws  Exception{
        ReadExcelUtil readExcelUtil = new ReadExcelUtil("hah.xlsx",new FileInputStream("D://g7_doc/产品信息0829(1).xlsx"));
        Map<String, Object> stringObjectMap = readExcelUtil.readExcelTitle("账单日");
        System.out.println(new Gson().toJson(stringObjectMap));

        Map<Integer, Map<String, Object>> data = readExcelUtil.readExcelContent(0, "账单日", 0);
        System.out.println(new Gson().toJson(data));
    }

    @Test
    public void readExcel() throws  Exception{
        List<ExcelOutData> excelOutData = ExcelUtil.readExcel(new FileInputStream("D://g7_doc/产品信息0829(1).xlsx"), ExcelOutData.class, 1);
        System.out.println(new Gson().toJson(excelOutData));
    }
}
