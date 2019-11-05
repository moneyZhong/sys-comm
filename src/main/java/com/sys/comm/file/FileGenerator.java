package com.sys.comm.file;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public class FileGenerator {

	public static FileInfo publicFilegenerate(File file, String UUIDStr,String publicFolder)
			throws Exception {
		FileBuilder builder = new PublicFileBuilder(file, UUIDStr);
		return builder.generateFile(publicFolder);
	}
	public static FileInfo publicFilegenerate(MultipartFile file, String UUIDStr ,String publicFolder)
			throws Exception {
		FileBuilder builder = new PublicFileBuilder(file, UUIDStr);
		return builder.generateFile(publicFolder);
	}
}
