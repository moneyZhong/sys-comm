package com.sys.dlb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GenNewSql {
//    public static String commentFileDir = "D:\\sql_script\\老动力宝库\\comments\\approval";
    public static String commentFileDir = "D:\\sql_script\\老动力宝库\\comments\\bill";
    public static void main(String[] args) {
        try {
            File file = new File("D:\\sql_script\\老动力宝库\\credit-bill.sql");
            String fileName = file.getName();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            Boolean startReadFlag = false;
            String tableName ="";
            StringBuilder sb = new StringBuilder("");
            while ((line = bufferedReader.readLine()) != null) {
                String columnName = "";
                String columnComm = "";

                if(line.startsWith(")")){
                    startReadFlag = false;
                }
                if(line.contains("COMMENT")){
                    startReadFlag = false;
                }

                if(startReadFlag){
                    if(!(line.indexOf("PRIMARY") > 0 || line.indexOf("UNIQUE") > 0|| line.indexOf("USING") > 0)){
                        columnName = line.trim();
//                        System.out.println(columnName);
                        columnName = columnName.substring(0,columnName.indexOf(" "));
                        columnName = columnName.substring(1,columnName.length()-1);
                        columnComm = getComment(tableName,columnName);
                    }
                }
                if(line.startsWith("CREATE")){
                    tableName = line.replace("CREATE","").replace("TABLE","").replace("(","").trim();
                    tableName = tableName.substring(1,tableName.length()-1);
                    startReadFlag = true;
                }

                sb.append(reCreateColumn(line,columnComm)).append("\r\n");
            }
            new  DlbDBOper().writeText(file.getParentFile().getPath()+ File.separator+fileName.substring(0,fileName.indexOf("."))+"_new.sql",sb.toString(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String reCreateColumn(String col, String comment){
        if(comment == null || "".equals(comment)){
            return col;
        }
         col = col.trim();
         col = col.substring(0,col.length() -1);
         return col+" comment'"+comment+"',";
    }

    public static String getComment(String tableName,String colName){
        String fileName = tableName.replaceFirst("t_","");
        File file = new File(commentFileDir+File.separator+fileName+".txt");
        System.out.println(file.getAbsolutePath());
        if(!file.exists()){
            file = new File(commentFileDir+File.separator+fileName.replaceAll("_","")+".txt");
            if(!file.exists()){
                System.out.println("不存在，返回空");
                return "";
            }

        }
        FileReader fileReader =null;
        BufferedReader bufferedReader = null;
        String returnComm = "";
        try{
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if("".equals(line)){
                    continue;
                }
                String[] array = line.split("&");
                if(colName.equalsIgnoreCase(array[1])){
                    returnComm = array[0];
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(fileReader != null){
                   fileReader.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        return returnComm;
    }
}
