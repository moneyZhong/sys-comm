package com.sys.comm.file;


import com.sys.comm.util.DateUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

public abstract class AbstractFileBuilder implements FileBuilder{

	private File file;

	private String UUIDStr;

	private String reallyName;
	
	private MultipartFile multipartFile;

	private String dateFolder = DateUtils.formatDateToString(new Date(),
			"yyyyMMdd");

	public AbstractFileBuilder(File file, String UUIDStr) {
		this.file = file;
		this.UUIDStr = UUIDStr;//文件别名
		this.reallyName = file.getName();
	}
	public AbstractFileBuilder(MultipartFile multipartFile, String UUIDStr) {
		this.multipartFile = multipartFile;
		this.UUIDStr = UUIDStr;//文件别名
		this.reallyName = multipartFile.getOriginalFilename();
	}

	@Override
	public void init(String publicFolder) throws Exception {

		checkFolder(publicFolder);
		File f = new File(getCurrentFolder() + File.separator
				+ UUIDStr);
		if (!f.exists()) {
			f.createNewFile();
		}

		if(file != null){
			FileUtils.copyFile(file, f);

		}
		if(multipartFile != null){
			write(multipartFile.getInputStream(),new FileOutputStream(getCurrentFolder() + File.separator
					+ UUIDStr));
		}
	}
	/**
     * 写入数据
     * @param in
     * @param out
     * @throws IOException
     */
    private  void write(InputStream in, OutputStream out) throws IOException{
        try{
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            in.close();
            out.close();
        }
    } 

	public abstract String getCurrentFolder();

//	private String getCurrentTempFolder() {
//		return TEMP_FOLDER + File.separator + dateFolder;
//	}

	protected String getCurrentPublicFolder(String publicfolder) {
		return publicfolder + File.separator + dateFolder;
	}

//	protected String getCurrentPrivateFolder() {
//		return PRIVATE_FOLDER + File.separator + dateFolder;
//	}

	/**
	 * 按日期生成文件夹
	 * 		yyyyMMdd
	 * @param parentPath
	 */
	private void checkFolder(String parentPath) {
		File file = new File(parentPath + File.separator + dateFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	

	/*@Override
	public String getTempFilePath() {
		return getCurrentTempFolder();
	}*/

	@Override
	public String getFileName() {
		if(file != null){
			return file.getName();
		}else{
			return multipartFile.getOriginalFilename();
		}
	}

	@Override
	public String getFilePath() {
		return getCurrentFolder();
	}

	public String getFileType() {
		return getFileName().substring(getFileName().lastIndexOf(".") + 1);
	};

	

	@Override
	public FileInfo generateFile(String publicFolder) throws Exception {
		init(publicFolder);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(reallyName);
		fileInfo.setFilePath((getFilePath()+File.separator+UUIDStr).replace(publicFolder, ""));//只要相对路径
		//fileInfo.setTempFilePath(getTempFilePath()+File.separator+UUIDStr);
		fileInfo.setFileType(getFileType());
		fileInfo.setCreateTime(new Date());
		return fileInfo;
	}
}
