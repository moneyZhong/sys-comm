package com.sys.dlb;


import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class DlbDBOper {
//    public static String fileDir = "D://g7_code/service-credit-approval/models";
//    public static String saveDir = "D:\\sql_script\\老动力宝库\\comments\\approval";

    public static String fileDir = "D:\\g7_code\\service-credit-bill\\database";
    public static String saveDir = "D:\\sql_script\\老动力宝库\\comments\\bill";

    public static Set<String> notReadFile;
    static{
        notReadFile = new HashSet<>();
        notReadFile.add("db.go");
    }
    public static void main(String[] args) {
        File files = new File(fileDir);
        File[] fileArray = files.listFiles();
        for(File file : fileArray){
            String fileName = file.getName();
            if(fileName.contains("generated") || notReadFile.contains(fileName)){
                continue;
            }
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                Boolean startReadFlag = false;
                String tableName = fileName.substring(0,fileName.indexOf("."));
                StringBuilder sbComment = new StringBuilder("");
                System.out.println(file.getAbsolutePath());
                while ((line = bufferedReader.readLine()) != null) {
                    if("".equals(line)){continue;}

                    if(line.startsWith("}")){
                        break;
                    }
                    if(startReadFlag){
                        if(line.contains("//")){
                            sbComment.append("\r\n");
                            sbComment.append(line.replace("//","").trim());
                            continue;
                        }
                        if(line.indexOf("json") < 0){continue;}
                        sbComment.append("&");
                        line = line.replace("db:\"","column:");
                        System.out.println(line);
                        String tmp = line.substring(line.indexOf("column")+7);
                        sbComment.append(tmp.substring(0,tmp.indexOf("\"")));
                        continue;
                    }

                   if(line.startsWith("type")){
                        startReadFlag = true;
                        String ss = line.substring(line.indexOf(" ")+1);
//                        tableName = ss.substring(0,ss.indexOf(" "));
                   }
                   continue;
                }
                writeText(saveDir+File.separator+tableName+".txt",sbComment.toString(),false);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

    }
    /*写入Text文件操作*/
    public static void writeText(String filePath, String content,boolean isAppend) {
        FileOutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(filePath,isAppend);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content);
            System.out.println(filePath+"成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
