package com.sys.comm.util;


import lombok.extern.slf4j.Slf4j;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Slf4j
public class FtpUtils {


	private final String FTP_CLIENT_ENCODING = "UTF-8";

	/**
	 * ftp服务器的ip地址
	 */
	private String ftpIp;
	/**
	 * ftp服务器的端口号
	 */
	private Integer ftpPort;
	/**
	 * ftp服务器的用户名
	 */
	private String ftpUsername;
	/**
	 * ftp服务器的密码
	 */
	private String ftpPassword;

	private FTPClient ftpClient;

	private String ftpPath;

	private FtpUtils(String ftpIp, Integer ftpPort, String ftpUsername, String ftpPassword, String ftpPath) {
		this.ftpIp = ftpIp;
		this.ftpPort = ftpPort;
		this.ftpUsername = ftpUsername;
		this.ftpPassword = ftpPassword;
		this.ftpPath = ftpPath;
		ftpClient = new FTPClient();
	}

//	public static FtpUtils getIntance() {
////		Config config = ConfigService.getAppConfig();
////		return new FtpUtils(config.getProperty("ftpIp", ""), Integer.valueOf(config.getProperty("ftpPort", "")),
////				config.getProperty("ftpUserName", ""), config.getProperty("ftpPassword", ""),
////				config.getProperty("ftpPath", ""));
//	}




	/**
	 * ftp服务器登录
	 *
	 * @return
	 */
	private boolean login() {
		boolean result = false;
		try {
			ftpClient.connect(this.ftpIp, this.ftpPort);
			result = ftpClient.login(this.ftpUsername, this.ftpPassword);
			initFtpClient();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("ftp服务器登录失败：", e);
		}
		return result;
	}

	private void initFtpClient() {
		try {
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding(FTP_CLIENT_ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean upload(String remote, String local) throws Exception {
		boolean result;
		if (org.apache.commons.lang3.StringUtils.isEmpty(remote)) {
			throw new IllegalArgumentException("this param remote is not empty!!!");
		}
		if (org.apache.commons.lang3.StringUtils.isEmpty(local)) {
			throw new IllegalArgumentException("this param local is not empty!!!");
		}
		File localFile = new File(local);
		if (!localFile.exists() || localFile.isDirectory()) {
			throw new IllegalArgumentException(local + " is not exists or is directory");
		}
		if (login()) {
			log.info("ftp服务器登录成功！！！");
		}
		File parentFile = new File(remote);
		String parent = parentFile.getParent();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
		this.makeDirectory(parent);
		InputStream is = new FileInputStream(local);
		
		result = ftpClient.storeFile(new String(remote.getBytes("UTF-8"), "ISO8859-1"), is);
		is.close();
		if (logout()) {
			log.info("ftp服务器退出登录成功！！！");
		}
		return result;
	}

	public List<File> downloandFtpDirectory(String localDirectory,String remoteDirecotroy) throws  Exception{
		List<File> re = new ArrayList<>();
		if(login()){
			log.info("ftp服务器登录成功！！！");
		}

		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
		boolean bool = ftpClient.changeWorkingDirectory(remoteDirecotroy);
		if(!bool){//变更目录失败
			if(logout()){
				log.info("ftp服务器登出！！！变更目录失败");
			}
			return re;
		}
		FTPFile[] files = ftpClient.listFiles();
		for(FTPFile file : files){
			if(file.isFile()){
				File localFile = new File(localDirectory+ File.separator+file.getName());
				if(localFile.exists()){
					continue;//如果本地服务器上已经存在文件不重复加载，解析。 如果需要重复解析，删除本地文件
				}
				if(!localFile.getParentFile().exists()){
					localFile.getParentFile().mkdirs();
				}
				if(!localFile.exists()){
					localFile.createNewFile();
				}
				OutputStream os = new FileOutputStream(localDirectory+ File.separator+file.getName());
				boolean result = ftpClient.retrieveFile(file.getName(),os);
				os.close();
				if(!result){
					if(logout()){
						log.info("ftp服务器登出！！！");
					}
					throw new RuntimeException("下载文件失败!");
				}
				re.add(localFile);
			}
		}
		if (logout()) {
			log.info("ftp服务器退出登录成功！！！");
		}
		return re;
	}

	public boolean downloadFtpFile(String localFile, String fileName)  {
		boolean result;
		try {
			if (login()) {
				log.info("ftp服务器登录成功！！！");
			}
			File file = new File(localFile);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
				log.info("文件不存在创建成功");
			}
			OutputStream os = new FileOutputStream(localFile);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
			ftpClient.changeWorkingDirectory(this.ftpPath);
			result = ftpClient.retrieveFile(fileName, os);
			log.info("文件下载成功");
			os.close();
			if (logout()) {
				log.info("ftp服务器退出登录成功！！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(StringUtils.getExceptionMsg(e));
			result = false;
		}
		return result;
	}

	/**
	 * 创建ftp远程路径
	 *
	 * @param remotePath
	 * @throws IOException
	 */
	private void makeDirectory(String remotePath) throws IOException {
		Stack<String> pathStack = new Stack<>();
		handlePath(remotePath, pathStack);
		while (!pathStack.isEmpty()) {
			ftpClient.makeDirectory(new String(pathStack.pop().getBytes("UTF-8"), "ISO8859-1"));
		}
	}

	/**
	 * 递归把路径存放到栈中
	 *
	 * @param remotePath
	 * @param pathStack
	 * @throws IOException
	 */
	private void handlePath(String remotePath, Stack<String> pathStack) throws IOException {
		File remote = new File(remotePath);
		if (remote.exists()) {
			return;
		} else {
			pathStack.push(remotePath);
			handlePath(remote.getParentFile().getPath(), pathStack);
		}
	}

	private boolean logout() {
		boolean result = false;
		try {
			result = ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("ftp服务器退出登录失败：", e);
		}
		return result;
	}


}
