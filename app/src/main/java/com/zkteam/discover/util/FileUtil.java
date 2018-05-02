package com.zkteam.discover.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 文件操作工具类
 */
public class FileUtil {

	/**
	 * 判断文件或目录是否存在
	 * @param f
	 * @return
	 */
	public static boolean checkExists(File f){

		return f == null ? false : f.exists();
	}

	/**
	 * 检查指定目录是否存在,如果不存在则创建
	 * @param dir
	 */
	public static boolean ensureDirExists(String dir) {

		if(TextUtil.isEmpty(dir))
			return false;

		return ensureDirExists(new File(dir));
	}

	/**
	 * 检查指定目录是否存在,如果不存在则创建
	 * @param dir
	 * @return
	 */
	public static boolean ensureDirExists(File dir){

		if(dir != null && dir.isDirectory()){

			return dir.exists() ? true : dir.mkdirs();
		}else{

			return false;
		}
	}

	/**
	 * 删除指定File，支持目录和文件
	 * @param file
	 */
	public static void deleteFile(File file) {

		if(file == null || !file.exists())
			return;

		if (file.isFile()) {

			file.delete();
		} else {

			File[] files = file.listFiles();

			if(files != null && files.length > 0){

				for (File f : files) {

					deleteFile(f);// 递归删除每一个文件
				}
			}

			file.delete();// 删除该文件夹
		}
	}

	/**
	 * 获取指定文件大小，支持文件和目录
	 * @param file
	 * @return
	 */
	public static long getSize(File file){

		if(file == null || !file.exists())
			return 0;

		if(file.isFile()){

			return file.length();
		}else{

			long total = 0;

			File[] files = file.listFiles();
			if(files != null){

				for(int i=0; i<files.length; i++){

					total += getSize(files[i]);
				}
			}

			return total;
		}
	}

	/**
	 * 将 src 文件拷贝到 dest
	 * @param src
	 * @param dest
	 * @return true 成功 false 失败
	 */
	public static boolean copyFile(String src, String dest){

		if(TextUtil.isEmpty(src) || TextUtil.isEmpty(dest))
			return false;

		return copyFile(new File(src), new File(dest));
	}

	/**
	 * 将 src 文件拷贝到 dest
	 * @param src
	 * @param dest
	 * @return true 成功 false 失败
	 */
	public static boolean copyFile(File src, File dest){

		FileInputStream fis = null;
		FileChannel fic = null;
		FileOutputStream fos = null;
		FileChannel foc = null;

		try{

			if(src == null || dest == null)
				return false;

			if(!src.exists())
				return false;

			if(!dest.exists())
				dest.createNewFile();

			fis = new FileInputStream(src);
			fic = fis.getChannel();

			fos = new FileOutputStream(dest);
			foc = fos.getChannel();

			foc.transferFrom(fic, 0, fic.size());

			return true;

		}catch(Exception e){

			e.printStackTrace();
		}finally{

			IOUtil.closeInStream(fis);
			IOUtil.closeOutStream(fos);
		}

		return false;
	}

	/**
	 * 获取文件的字节数组
	 * @param filePath
	 * @return
	 */
	public static byte[] getFileBytes(String filePath) {

		if(filePath == null)
			return null;

		FileInputStream input = null;
		ByteArrayOutputStream baos = null;

		try {

			input = new FileInputStream(filePath);
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = input.read(buffer)) != -1) {

				baos.write(buffer, 0, len);
			}

			return baos.toByteArray();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			IOUtil.closeInStream(input);
			IOUtil.closeOutStream(baos);
		}

		return null;
	}

	/**
	 * 关闭随机访问文件
	 * @param file
	 */
	public static void closeRandomAccessFile(RandomAccessFile file){

		try{

			if(file != null)
				file.close();

		}catch(Exception e){

			e.printStackTrace();
		}
	}

	/**
	 * 序列化存储对象
	 * @param obj
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static boolean writeObj(Object obj, File dir, String fileName) {

		if(dir == null || TextUtil.isEmpty(fileName))
			return false;

		return writeObj(obj, new File(dir, fileName));
	}

	/**
	 * 序列化存储对象
	 * @param obj
	 * @param f
	 */
	public static boolean writeObj(Object obj, File f) {

		if(obj == null || f == null)
			return false;

		ObjectOutputStream oos = null;

		try {

			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(obj);
			return true;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			IOUtil.closeOutStream(oos);
		}

		return false;
	}

	/**
	 * 序列化读取对象
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static Object readObj(File dir, String fileName){

		if(dir == null || TextUtil.isEmpty(fileName))
			return null;

		return readObj(new File(dir, fileName));
	}

	/**
	 * 序列化读取对象
	 * @param f
	 * @return
	 */
	public static Object readObj(File f) {

		if(f == null || !f.exists())
			return null;

		ObjectInputStream oic = null;

		try {

			oic = new ObjectInputStream(new FileInputStream(f));
			return oic.readObject();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			IOUtil.closeInStream(oic);
		}

		return null;
	}

}
