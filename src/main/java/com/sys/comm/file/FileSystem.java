package com.sys.comm.file;

import com.sys.comm.util.UUIDUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileSystem {
    public List<FileInfo> savePublicFile(HttpServletRequest request,String publicFolder) throws Exception {
        List<FileInfo> list = new ArrayList<FileInfo>();
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            /*//判断文件名称是否重复
            if(haveSomeName(iter)){iter中存放的是file的name 属性值，不是文件名称
            	throw new RuntimeException("含有重复文件!");
            }*/
            int i = 0;
            while(iter.hasNext()){
                i++;
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if("".equals(file.getOriginalFilename())){
                    continue;
                }
                String UUIDStr = "pl_" + UUIDUtil.getStringUUid() + "."
                        + getFileType(file);
                FileInfo fileInfo = FileGenerator.publicFilegenerate(file, UUIDStr,publicFolder);
                //  fileInfoDao.insert(fileInfo);
                list.add(fileInfo);
            }
            if(i == 0){
                throw new RuntimeException("未上传文件或file的name属性未设置!");
            }
        }else{
            throw new RuntimeException("非文件上传请求！");
        }
        return list;
    }
    private String getFileType(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }
    private String getFileType(MultipartFile file){
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }
    private Boolean isImageFile(String fileType) {
        Boolean flag = false;
        // 常用六种格式
        String[] splitS = new String[] { "bmp", "jpg", "gif", "png", "jpeg",
                "psd" };
        for (String split : splitS) {
            if (split.equalsIgnoreCase(fileType)) {
                flag = true;
            }
        }
        return flag;
    }
}
