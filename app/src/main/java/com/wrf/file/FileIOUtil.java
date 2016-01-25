package com.wrf.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileIOUtil {
	public static String SDPATH = Environment.getExternalStorageDirectory() + "/juewei";
	/**
	 * 保存图片文件
	 * 
	 * @param hpByte
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static boolean saveImage(byte[] hpByte, String path, String fileName)
			throws IOException {

		try {

			File fileDir = new File(path);
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			File hpFile = new File(fileDir, fileName);
			if (hpFile.exists()) {
				hpFile.delete();
			}
			FileOutputStream outStream = new FileOutputStream(hpFile);
			outStream.write(hpByte);
			outStream.close();

		} catch (Exception e) {

			Log.e("yunlai", e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 保存图片文件
	 * 
	 * @param hpByte
	 * @param filePath
	 *            包括了文件名
	 * @return
	 * @throws IOException
	 */

	public static boolean saveImage(byte[] hpByte, String filePath)
			throws IOException {

		try {
			String path = filePath.substring(0, filePath.lastIndexOf("/"));
			File fileDir = new File(path);
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}

			File hpFile = new File(filePath);
			if (hpFile.exists()) {
				hpFile.delete();
			}
			FileOutputStream outStream = new FileOutputStream(hpFile);
			outStream.write(hpByte);
			outStream.close();

		} catch (Exception e) {

			Log.e("yunlai", e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 读取图片文件，返回Bitmap
	 * 
	 * @param file
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public static Bitmap readImage(String file) throws Exception {
		Bitmap bitmap = null;

		try {
			File imgFile = new File(file);
			if (!imgFile.exists()) {
				return null;
			}

			bitmap = BitmapFactory.decodeFile(file);

		} catch (Exception e) {

			Log.e("yunlai", e.getMessage());
		}

		return bitmap;
	}

	/**
	 * 删除文件夹所有文件(没有递归删除)
	 * 
	 * @param path
	 */
	public static void deleteFiles(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}

		if (!file.isDirectory()) {
			return;
		}

		String[] tempList = file.list();
		File temp = null;

		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}

			if (temp.isFile()) {
				temp.delete();
			}

		}
	}

	/**
	 * 删除文件夹所有文件(没有递归删除)
	 * 
	 * @param path
	 */
	public static void deleteFilesRecursion(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}

		if (!file.isDirectory()) {
			return;
		}

		String[] tempList = file.list();
		File temp = null;

		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}

			if (temp.isFile()) {
				temp.delete();
			} else if (temp.isDirectory()) {
				deleteFilesRecursion(temp.getAbsolutePath());
			}

		}
	}

	public static void moveFile(String sourceFile, String descFile)
			throws IOException {

		File a = new File(sourceFile);
		File b = new File(descFile);
		if (!b.exists()) {
			b.createNewFile();
		}

		// 实例输入输出的文件流
		InputStream in = null;
		OutputStream out = null;
		BufferedInputStream inb = null;
		BufferedOutputStream oub = null;
		try {
			// 构建文件输入流
			in = new FileInputStream(a);
			// 构建文件输出流
			out = new FileOutputStream(b);
			inb = new BufferedInputStream(in);
			oub = new BufferedOutputStream(out);

			// 开始复制
			byte[] read = new byte[1024];
			int len = in.read(read);
			while (len != -1) {
				oub.write(read, 0, len);
				len = in.read(read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				oub.close();
				inb.close();
				out.close();
				in.close();
			} catch (Exception e0) {

				e0.printStackTrace();
			}
		}

		a.delete();
	}

	/**
	 * 把byte写入文件
	 * 
	 * @param dir
	 * @param file
	 * @param data
	 * @param isAdd
	 */
	public static void writeFile(String dir, String file, byte[] data,
			boolean isAdd) {
		try {
			File path = new File(dir);
			File f = new File(dir + file);
			if (!path.exists()) {
				path.mkdirs();
			}
			if (!f.exists()) {
				f.createNewFile();

			}
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(data);
			fos.close();
		} catch (IOException e) {

			Log.e("yunlai", e.getMessage());
		}

	}

	/**
	 * 读取文件
	 * 
	 * @param dir
	 * @param file
	 * @param data
	 * @param isAdd
	 */
	public static String readTxtFile(String filePath) {
		String result = "";
		try {
			InputStream instream = new FileInputStream(filePath);
			if (instream != null) {
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line;
				// 分行读取
				while ((line = buffreader.readLine()) != null) {
					result += line + "\n";
				}
				instream.close();
			}
		} catch (IOException e) {

			Log.e("yunlai", e.getMessage());
			return "";
		}
		return result;
	}

	public static void writeObject(Object obj,String fileName,Context context) {
		try {
			//File cacheFile = StorageUtils.getCacheDirectory(context);
			File file = new File(SDPATH);
			if(!file.exists())
				file.mkdir();
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);//new FileOutputStream(SDPATH + "/" +fileName);//new File(cacheFile, fileName));

			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(obj);

			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static Object readObject(String fileName , Context context) {
		Object obj = null;
		try {
			//File cacheFile = StorageUtils.getCacheDirectory(context);
			FileInputStream fis =  context.openFileInput(fileName);//new FileInputStream(SDPATH + "/" +fileName);

			ObjectInputStream ois = new ObjectInputStream(fis);

			obj =  ois.readObject();

			ois.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		}
		return obj;
	}
	
	public static void fillData(Object object,String cachFileName,Context context){
		Object cachObj = readObject(cachFileName,context);
		if(null != cachObj){
			object = cachObj;
		}
	}

}
