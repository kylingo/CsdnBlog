package com.free.csdn.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

@SuppressLint("NewApi")
public class FileUtils {

	private static final double KB = 1024.0;
	private static final double MB = KB * KB;
	private static final double GB = KB * KB * KB;

	/** 获取文件后缀 */
	public static String getFileExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

	/**
	 * 获取url文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlFileName(String url) {
		int slashIndex = url.lastIndexOf('/');
		int dotIndex = url.lastIndexOf('.');
		String filenameWithoutExtension;
		if (dotIndex == -1) {
			filenameWithoutExtension = url.substring(slashIndex + 1);
		} else {
			filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
		}
		return filenameWithoutExtension;
	}

	/**
	 * 获取url后缀
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlExtension(String url) {
		if (!TextUtils.isEmpty(url)) {
			int i = url.lastIndexOf('.');
			if (i > 0 && i < url.length() - 1) {
				return url.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}

	/**
	 * 获取文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 格式化大小
	 * 
	 * @param size
	 * @return
	 */
	public static String formatSize(long size) {
		String fileSize;
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + " KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + " MB";
		else
			fileSize = String.format("%.1f", size / GB) + " GB";

		return fileSize;
	}

	/** 显示SD卡剩余空间 */
	public static String showFileAvailable() {
		String result = "";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			long availCount = sf.getAvailableBlocks();
			return formatSize(availCount * blockSize) + " / " + formatSize(blockSize * blockCount);
		}
		return result;
	}

	/** 如果不存在就创建 */
	public static boolean createIfNoExists(String path) {
		File file = new File(path);
		boolean mk = false;
		if (!file.exists()) {
			mk = file.mkdirs();
		}
		return mk;
	}

	/**
	 * 获取SD卡路径
	 * 
	 * @param c
	 * @return
	 */
	public static String getSdCardPath(Context c) {
		// ToastUtils.showToast(paths.toString());
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}

		return "";
	}

	/**
	 * SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean getSdAvailable() {
		return Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())
				|| Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 获取文件mime类型
	 */
	public static String getFileMimeType(File file) {
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(file));
		if (type == null)
			return "*/*";
		return type;
	}

	/**
	 * 获取下载存储地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getDownloadSavePath(Context context) {
		return context.getExternalFilesDir(null).getAbsolutePath();
	}

	/**
	 * 获取Sd卡图片资源
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getSdcardImage(String path) {
		File mFile = new File(path);
		// 若该文件存在
		if (mFile.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			return bitmap;
		}

		return null;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @param size
	 * @return
	 */
	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 计算目录大小
	 * 
	 * @param f
	 * @return 大小
	 * @throws Exception
	 */
	public static long getFileSize(File f) {
		if (f == null) {
			return 0;
		}
		long size = 0;
		File flist[] = f.listFiles();
		for (File file : flist) {
			if (file.isDirectory()) {
				size = size + getFileSize(file);
			} else {
				size = size + file.length();
			}
		}
		return size;
	}

	/**
	 * 获取缓存目录（/SD卡/Android/data/【PackageName】/cache）
	 * 
	 * @param context
	 * @return
	 */
	public static String getExternalCacheDir(Context context) {
		String path = context.getExternalCacheDir().getAbsolutePath();
		return path;
	}
}
