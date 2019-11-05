package com.sys.comm.file;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class PublicFileBuilder extends AbstractFileBuilder{

    public PublicFileBuilder(File file, String UUIDStr) {
        super(file, UUIDStr);
    }
    public PublicFileBuilder(MultipartFile file, String UUIDStr) {
        super(file, UUIDStr);
    }

    @Override
    public String getCurrentFolder(String publicFolder) {
        return super.getCurrentPublicFolder(publicFolder);
    }
}
