package com.sys.comm.file;




public interface FileBuilder {
	public void init(String publicFolder) throws Exception;

	//public String getTempFilePath();
	
	public String getFileName();
	
	public String getFilePath();
	
	public String getFileType();
	
	public FileInfo generateFile(String publicFolder)throws Exception;
}
