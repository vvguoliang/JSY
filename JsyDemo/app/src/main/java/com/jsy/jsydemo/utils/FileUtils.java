package com.jsy.jsydemo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 *  vvguoliang 2017-6-23
 * File Utils
 *   文件缓存数据位置
 */
public class FileUtils {

	public final static String FILE_EXTENSION_SEPARATOR = ".";

	private FileUtils() {
		throw new AssertionError();
	}

	/**
	 * 获得下载目录路径
	 * 
	 * @return 外置内存卡下载路径
	 */
	public static String getExternalDownloadPath() {
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
	}

	/**
	 * 获得外部目录路径
	 * 
	 * @return 外置内存卡外部路径
	 */
	public static String getExternalPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 获得程序定义根路径下的一个文件，如果该文件存在，那么返回这个文件的名字， 如果文件不存在，创建这个文件，和这个文件所在的文件夹
	 * 
	 * @return 外置内存卡根路径
	 */
	public static String getSxsExternalStorageFilePath(Context context, String fileName) {
		File file = new File(context.getExternalFilesDir(null), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * 获取崩溃日志文件夹
	 * 
	 * @return
	 */
	public static String getCeHandler() {
		return getExternalPath() + File.separator + ".sxsCeHandler";
	}

	/**
	 * 获得程序定义根路径下的一个文件，如果该文件存在，那么返回这个文件的名字， 如果文件不存在，创建这个文件，和这个文件所在的文件夹
	 * 
	 * @return 外置内存卡根路径
	 */
	public static String getExternalStorageFilePath(Context context, String fileName) {
		File file = new File(getCeHandler(), fileName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * read file
	 * 
	 * @param filePath
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}
	 * @return if file not exist, return null, else return content of file
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static StringBuilder readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (!file.isFile()) {
			return null;
		}

		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			BufferedReader reader = new BufferedReader(is);
			String line;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}

	/**
	 * write file
	 * 
	 * @param filePath
	 * @param content
	 * @param append
	 *            is append, if true, write to the end of file, else clear
	 *            content of file and write into it
	 * @return return false if content is empty, true otherwise
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content, boolean append) {
		if (content == null || "".equals(content) || "null".equals(content)) {
			return false;
		}

		FileWriter fileWriter;
		try {
			if (!new File(filePath).getParentFile().exists()) {
				new File(filePath).getParentFile().mkdirs();
			}
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * write file, the string will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * read file to string list, a element of list is a line
	 * 
	 * @param filePath
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}
	 * @return if file not exist, return null, else return content of file
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static List<String> readFileToList(String filePath, String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<>();
		if (!file.isFile()) {
			return null;
		}

		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			BufferedReader reader = new BufferedReader(is);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}

	/**
	 * 获取文件的md5
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.exists() || file.isDirectory()) {
			return null;
		}
		MessageDigest digest;
		FileInputStream in;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return toHexString(digest.digest());
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
}
