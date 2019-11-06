package com.sys.util.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;
@Data
public class ExcelOutData extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String serialNum;

    @ExcelProperty(index = 1)
    private String carNo;

    @ExcelProperty(index = 2,format = "yyyy-MM-dd")
    private Date buyDate;

    @ExcelProperty(index = 3)
    private String belongType;

    @ExcelProperty(index = 4)
    private String isMortgage;

    @ExcelProperty(index = 5)
    private String carLength;

    @ExcelProperty(index = 6)
    private String belongPerson;
}
