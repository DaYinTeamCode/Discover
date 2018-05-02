package com.zkteam.discover.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * sdcard工具类
 */
public class StorageUtil {

	private static String appHomeDirPath = "/mnt/sdcard/exapp";// default
	private static String fileDir = "files/";//android:files
	private static String picDir = "pictures/";//pics
	private static String cacheDir = "cache/"; //android: cache
	private static String dataBaseDir = "databases/";//android: databases
	private static String logDir = "log/";

	/**
	 * 设置app sdcard的应用主目录
	 */
	public static void initAppHomeDir(Context context) {

		//先获取sdcard卡 android data 主目录
		File dataPkgDir = getSdcardAndroidDataPackageDir(context);
		if(dataPkgDir == null){

			//再尝试获取external映射的 android data 主目录
			dataPkgDir = context.getExternalCacheDir();
			if(dataPkgDir != null)
				dataPkgDir = dataPkgDir.getParentFile();
		}

		if(canWriteFile(dataPkgDir)){

			appHomeDirPath = dataPkgDir.getAbsolutePath();
		}else{

			dataPkgDir = context.getCacheDir();
			if(dataPkgDir != null){

				dataPkgDir = dataPkgDir.getParentFile();
				if(dataPkgDir != null)
					appHomeDirPath = dataPkgDir.getAbsolutePath();
			}
		}

		//做个兜底容错
		if(dataPkgDir == null || TextUtil.isEmpty(appHomeDirPath))
			appHomeDirPath = "/mnt/sdcard/Android/data/"+context.getPackageName();
	}

	public static File getSdcardAndroidDataPackageDir(Context context){

		return getSdcardSubDir("Android/data/"+context.getPackageName());
	}

	/**
	 * 获取app sdcard 应用主目录
	 * @return
	 */
	public static File getAppHomeDir() {

		File dir = new File(appHomeDirPath);
		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取app sdcard 主目录下 files目录
	 * @return
	 */
	public static File getAppFileDir() {

		File dir = new File(getAppHomeDir(), fileDir);

		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取app sdcard 主目录下 files目录下文件
	 * @return 如果fileName为空返回null
	 */
	public static File getAppFileDirFile(String fileName) {

		if(TextUtil.isEmpty(fileName))
			return null;

		return new File(getAppFileDir(), fileName);
	}

	/**
	 * 获取app sdcard 主目录下 files目录下文件
	 * @return 如果fileName为空返回null
	 */
	public static File getAppFileDirSubDir(String dirName) {

		File f = new File(getAppFileDir(), TextUtil.filterNull(dirName));
		if(!f.exists())
			f.mkdirs();

		return f;
	}

	/**
	 * 获取app sdcard 主目录下 pics目录
	 * @return
	 */
	public static File getAppPicDir() {

		File dir = new File(getAppHomeDir(), picDir);

		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取app sdcard 主目录下 pics目录下文件
	 * @return 如果fileName为空返回null
	 */
	public static File getAppPicDirFile(String fileName) {

		if(TextUtil.isEmpty(fileName))
			return null;

		return new File(getAppPicDir(), fileName);
	}

	/**
	 * 获取app sdcard 主目录下 caches目录
	 * @return
	 */
	public static File getAppCacheDir() {

		File dir = new File(getAppHomeDir(), cacheDir);

		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取app sdcard 主目录下 caches目录下文件
	 * @return 如果fileName为空返回null
	 */
	public static File getAppCacheDirFile(String fileName) {

		if(TextUtil.isEmpty(fileName))
			return null;

		return new File(getAppCacheDir(), fileName);
	}

	/**
	 * 获取app sdcard 主目录下 databases目录
	 * @return
	 */
	public static File getAppDatabaseDir() {

		File dir = new File(getAppHomeDir(), dataBaseDir);

		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取app sdcard 主目录下 databases目录下文件
	 * @return 如果fileName为空返回null
	 */
	public static File getAppDatabaseDirFile(String fileName) {

		if(TextUtil.isEmpty(fileName))
			return null;

		return new File(getAppDatabaseDir(), fileName);
	}

	/**
	 * 获取app sdcard 主目录下 log目录
	 * @return
	 */
	public static File getAppLogDir(){

		File dir = new File(getAppHomeDir(), logDir);

		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 判断sdcard是否可用
	 * @return
	 */
	public static boolean sdcardIsEnable() {

		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) && !Environment.MEDIA_SHARED.equals(state);
	}

	/**
	 * 判断sdcard是否挂载
	 * @return
	 */
	public static boolean sdcardIsMounted() {

		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 获取sdcard目录
	 * @return
	 */
	public static File getSdcardDir() {

		File dir = Environment.getExternalStorageDirectory();
		if(!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取sdcard下的子目录
	 * @return subDirName为空，则返回的是sdcard目录
	 * @return
	 */
	public static File getSdcardSubDir(String subDirName) {

		File dir = new File(getSdcardDir(), TextUtil.filterNull(subDirName));

		if(!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取sdcard根目录DCIM目录
	 * @return
	 */
	public static File getSdcardDCIMDir(){

		return getSdcardSubDir(Environment.DIRECTORY_DCIM);
	}

	/**
	 * 获取sdcard根目录DCIM目录的子目录
	 * @return subDirName为空，则返回的是DCIM目录
	 */
	public static File getSdcardDCIMSubDir(String subDirName){

		File dir = new File(getSdcardDCIMDir(), TextUtil.filterNull(subDirName));

		if(!dir.exists())
			dir.mkdirs();

		return dir;
	}

	/**
	 * 获取sdcard根目录DCIM目录的Camera
	 * @return
	 */
	public static File getSdcardDCIMCameraDir(){

		return getSdcardDCIMSubDir("Camera");
	}

	/**
	 * 获取sdcard目录 DCIM/Camrea/xxx 文件
	 * @return
	 */
	public static String getSdcardDCIMCameraDirFile(String fileName){

		return new File(getSdcardDCIMCameraDir(), TextUtil.filterNull(fileName)).getAbsolutePath();
	}

	/**
	 * 获取download目录
	 * @return
	 */
	public static File getSdcardDownloadDir(){

		return getSdcardSubDir(Environment.DIRECTORY_DOWNLOADS);
	}

	public static boolean canWriteFile(File dir){

		try{

			if(dir == null)
				return false;

			if(!dir.exists())
				dir.mkdirs();

			File f = new File(dir, "cwf");
			if(f.exists())
				f.delete();

			return f.createNewFile();

		}catch(Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 历史遗留问题：原来的图片缓存目录叫pics
	 * 后来更名为pictures
	 * 提供该方法用来删除原来的pics目录
	 */
	public static void asyncDeleteDir(final File dir){

		if(dir == null)
			return;

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				try {

					if(dir.exists())
						FileUtil.deleteFile(dir);

				} catch (Throwable e) {

				}

				return null;
			}

		}.execute();
	}

	public static void asyncCreateNomediaFileInHomeDir() {


		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				try {

					File noMediaFile = new File(getAppHomeDir(), ".nomedia");
					if (!noMediaFile.exists())
						noMediaFile.createNewFile();

				} catch (IOException e) {

				}

				return null;
			}

		}.execute();

	}
}