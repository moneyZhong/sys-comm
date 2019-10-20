package com.sys.comm.enums;

public enum  DateFormatEnum {
    yyyy_MM_dd("yyyy-MM-dd","年-月-日"),
   yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss","年-月-日 时:分:秒");

    private String code;
    private String name;
    DateFormatEnum(String code,String name){
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
    public static DateFormatEnum getInstance(String code) {
        if (code != null) {
            for (DateFormatEnum resourceEnum : DateFormatEnum.values()) {
                if (resourceEnum.getCode().equals(code)) {
                    return resourceEnum;
                }
            }
        }
        return null;
    }
}
