package com.gsww.uids.gateway.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

/**
 * 文件上传通用类
 * 
 * @author gongzhy 20080730
 */
public class UploadFileToServer {
	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	private static void createPath(String path) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		File f = new File(path);
		if (f.exists())
			f.delete();
	}

	/**
	 * 文件大小转换
	 * 
	 * @param fileLen
	 * @return
	 */
	public static String getFileLength(long len) {
		String result = "";
		if (len >= 1024 * 1024) {
			BigDecimal f = new BigDecimal(len / (1024 * 1024.0)).setScale(2, BigDecimal.ROUND_HALF_UP);
			result = f.toString() + "M";
		} else if (len >= 1024) {
			BigDecimal f = new BigDecimal(len / 1024.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			result = f.toString() + "K";
		} else {
			result = Long.toString(len) + "B";
		}
		return result;
	}

	/**
	 * 上传到磁盘
	 * 
	 * @param fileName
	 * @param uploadFile
	 * @param savePath
	 * @return
	 */
	public static boolean uploadToDisk(File uploadFile, String savePath) {
		String saveDir = savePath.substring(0, savePath.lastIndexOf("/"));
		createPath(saveDir);
		try {
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(new FileOutputStream(savePath));
				in = new BufferedInputStream(new FileInputStream(uploadFile));
				int c = 0;
				while ((c = in.read()) != -1)
					out.write(c);
			} finally {
				in.close();
				out.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 上传到数据库
	 * 
	 * @param uploadFile
	 * @return
	 */
	public static byte[] uploadToDB(File uploadFile) {
		try {
			byte[] data = null;
			FileInputStream in = new FileInputStream(uploadFile);
			data = new byte[(int) uploadFile.length()];
			in.read(data);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
}
