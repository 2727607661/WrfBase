package com.wrf.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.wrf.utils.LogUtils;
import com.wrf.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * read file
     *
     * @param filePath
     * @return if file not exist, return null, else return content of file
     * @throws IOException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
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
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param content
     * @param append   is append, if true, write to the end of file, else clear content of file and write into it
     * @return return true
     * @throws IOException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param stream
     * @return return true
     * @throws IOException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        OutputStream o = null;
        try {
            o = new FileOutputStream(filePath);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @return if file not exist, return null, else return content of file
     * @throws IOException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }


    public static byte[] ReadFileBytes(String file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(new File(file), "r");

        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");

            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }

    public static InputStream ReadFileInputStream(String file) throws IOException {
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inStream;
    }

/*	public static Object ReadFromJsonData(byte[] buffer, Class cls) {
        if (null == buffer)
			return null;
		String data;
		try {
			data = new String(buffer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		Gson gson = new Gson();
		Object obj;

		try {
			obj = gson.fromJson(data, cls);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}

		return obj;
	}*/

    public static <T> T ReadFromJsonData(byte[] buffer, Class<T> cls) {
        if (null == buffer || cls == null)
            return null;
        String data;
        try {
            data = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();
        T t;

        try {
            t = gson.fromJson(data, cls);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return t;
    }

    public static Object ReadFromJsonData(byte[] buffer, Type type) {
        if (null == buffer || type == null)
            return null;
        String data;
        try {
            data = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();
        Object obj;

        try {
            obj = gson.fromJson(data, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }

/*	public static Object ReadFromJsonFile(String fn, Class cls) {

		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(fn));
		} catch (FileNotFoundException e1) {
			return null;
		}

		Gson gson = new Gson();
		Object obj;

		try {
			obj = gson.fromJson(reader, cls);
		} catch (JsonIOException e) {
			e.printStackTrace();
			return null;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}

		return obj;
	}*/

    public static <T> T ReadFromJsonFile(String fn, Class<T> cls) {
        if (null == fn || cls == null)
            return null;
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(fn));
        } catch (FileNotFoundException e1) {
            return null;
        }

        Gson gson = new Gson();
        T t;

        try {
            t = gson.fromJson(reader, cls);
        } catch (JsonIOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return t;
    }

    public static Object ReadFromJsonFile(String fn, Type type) {
        if (null == fn || type == null)
            return null;
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(fn));
        } catch (FileNotFoundException e1) {
            return null;
        }

        Gson gson = new Gson();
        Object obj;

        try {
            obj = gson.fromJson(reader, type);
        } catch (JsonIOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }

    public static void SaveJson(byte[] buffer, String fn) {
        if (null == buffer || fn == null)
            return;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fn);
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取SD卡的路径
     */
    public static String getSDPath() {
        File sdDir = null;
        //判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir == null ? null : sdDir.toString();
    }


    /**
     * 获取文件列表（可根据文件后缀名过滤）
     *
     * @param sourceDir
     * @param filters   eg:com.xxx.xxx | .zip |!lib !表示非
     * @return
     * @throws IOException
     */
    public static List<File> getFileList(String sourceDir, String... filters) {
        List<File> filelist = new ArrayList<File>();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; file != null && i < file.length; i++) {
            if (file[i].isAbsolute()) {
                // 源文件
                File sourceFile = file[i];
                // 过滤
                if (filters != null && !filters.equals("")) {
                    boolean res = true;
                    for (String filter : filters) {
                        if (filter.indexOf("!") != -1) {
                            if (sourceFile.getName().contains(filter.substring(filter.indexOf("!") + 1, filter.length()))) {
                                res = false;
                                break;
                            }
                        } else {
                            if (!sourceFile.getName().contains(filter)) {
                                res = false;
                                break;
                            }
                        }
                    }
                    if (res) {
                        filelist.add(sourceFile);
                    }
                } else {
                    filelist.add(sourceFile);
                }
            }
        }
        return filelist;
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }


    /**
     * 把文件转换为GBK文件
     *
     * @param srcFileName
     * @param destFileName
     * @param srcCoding
     * @param destCoding
     * @throws IOException
     */
    public static void copyFile(File srcFileName, File destFileName, String srcCoding, String destCoding) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileName), srcCoding));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFileName), destCoding));
            char[] cbuf = new char[1024 * 5];
            int len = cbuf.length;
            int off = 0;
            int ret = 0;
            while ((ret = br.read(cbuf, off, len)) > 0) {
                off += ret;
                len -= ret;
            }
            bw.write(cbuf, 0, off);
            bw.flush();
        } finally {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
        }
    }

    /**
     * @param filepath
     * @throws IOException
     */
    public static void del(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        }
    }


    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirectorySize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirectorySize(file); //递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String FormatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 创建目录
     *
     * @param destDirName 目标目录名
     * @return 目录创建成功返回true，否则返回false
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            //System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return true;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            //System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            // System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * 将数据缓存到文件
     *
     * @param context
     * @param name    缓存文件名
     * @param data    数据
     */
    public static void saveDataToFile(Context context, String name, String data) {
        if (StringUtils.isEmpty(name)) return;
        String path = toCachePath(context, name);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存数据
     * <p>
     * Description:
     * </p>
     *
     * @param context
     * @param name    缓存文件名称
     * @param entity  需要转换为的实体类
     * @return
     */
    public static <T> T getCacheData(Context context, String name, Class<T> entity) {
        if (StringUtils.isEmpty(name)) return null;
        String path = toCachePath(context, name);
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(path));
        } catch (FileNotFoundException e1) {
            return null;
        }

        Gson gson = new Gson();
        T obj;

        try {
            obj = gson.fromJson(reader, entity);
        } catch (JsonIOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * 删除缓存数据
     *
     * @param context
     * @param name    缓存文件名称
     * @return
     */
    public static void deleteCacheData(Context context, String name) {
        if (StringUtils.isEmpty(name)) return;
        String path = toCachePath(context, name);
        try {
            del(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换缓存文件路径
     *
     * @param context
     * @param name
     * @return
     */
    public static String toCachePath(Context context, String name) {
        return context.getFilesDir().getAbsolutePath() + "/" + name + ".txt";
    }

    /**
     * 将字符串post请求数据体转换成请求所需结构体
     *
     * @param data
     * @return
     */
    public static Map<String, InputStream> toPostDataMap(
            Map<String, String> data) {
        Map<String, InputStream> dataMap = new HashMap<String, InputStream>();
        List<String> keyList = new ArrayList<String>();
        for (String string : data.keySet()) {
            keyList.add(string);
        }
        for (int i = 0; i < data.size(); i++) {
            if (!StringUtils.isEmpty(data.get(keyList.get(i)))) {
                try {
                    InputStream is = new ByteArrayInputStream(data.get(
                            keyList.get(i)).getBytes("UTF-8"));
                    dataMap.put(keyList.get(i), is);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataMap;
    }

    /**
     * 将图片转换成输入流
     * <p>
     * Description:
     * </p>
     *
     * @param filePath
     * @param reqHeight
     * @param reqWidth
     * @return
     */
    public static InputStream convertToBitmap(String filePath, int reqHeight,
                                              int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(filePath, options);

        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        InputStream is = Bit2Input(bitmap);

        bitmap.recycle();
        bitmap = null;

        return is;
    }

    public static InputStream Bit2Input(Bitmap bm) {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bas);
        InputStream is = new ByteArrayInputStream(bas.toByteArray());
        return is;
    }


    /**
     * 删除文件夹下所有文件
     * <p>Description: </p>
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    LogUtils.log("删除文件：" + f.getPath());
                    deleteFile(f);
                }
//                file.delete();//不删除文件夹 
            }
        }

    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (StringUtils.isEmpty(fileName)) return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }
}
