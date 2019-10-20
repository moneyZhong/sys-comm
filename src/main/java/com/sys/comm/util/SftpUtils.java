package com.sys.comm.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

@Slf4j
@Data
public class SftpUtils {

	private String host;
	private String username;
	private String password;
	private int port;
	private ChannelSftp sftp = null;
	private Session sshSession = null;
	
	public ChannelSftp connectSftp() {
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            log.info("SFTP Session connected.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("Connected to " + host);
		}catch(Exception ex) {
			log.error("连接sftp异常：{}",ex);
		}
		return sftp;
	}
	
	//判断本地文件是否存在
	public boolean localFileExists(String fileDirec,String fileName) {
		File fileDir =new File(fileDirec);
		if(fileDir.isDirectory()) {
			//获取文件
			File []file= fileDir.listFiles();
			for(int i=0;i<file.length;i++) {
				File f= file[i];
				if(f.isFile() && f.getName().equalsIgnoreCase(fileName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getFilePath(String dir,String path,String fileName) {
//		boolean b = localFileExists(path, fileName);
		String saveFile = path + File.separator + fileName;
		connectSftp();
		download(dir, fileName, saveFile);
		return saveFile;
	}
	
	/**
     	* 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public File download(String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            if (!file.getParentFile().exists()){
            	file.getParentFile().mkdirs();
			}
            if(file.exists()){
				log.info("pfx文件已存在，执行删除。");
				file.delete();
			}
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile, fileOutputStream);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
