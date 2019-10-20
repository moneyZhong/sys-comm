package com.sys.dlb;

public class Test {
    public static void main(String[] args) {
        String abc = "ID uint64 `column:F_id\" sql:\"bigint(64) unsigned NOT NULL AUTO_INCREMENT\" json:\"id\"`";
        System.out.println(abc.indexOf("column"));
        System.out.println(  abc.substring(abc.indexOf("column")+7));
    }
}
