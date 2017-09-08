package com.liuqi.screenqueen.domin.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author liuqi 2017/9/2 0002
 * @Package com.liuqi.screenqueen.domin.utils
 * @Title: FileUtil
 * @Description: (function)
 * Copyright (c) liuqi owner
 * Create DateTime: 2017/9/2 0002
 */
public class FileUtil {
    /**
     * 拍照路径
     */
    public static String PATH_PHOTOGRAPH = "/Beauty/";

    public static File getOutFile(String filePath, String imageName) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用
            File dirs = new File(Environment.getExternalStorageDirectory(),
                    filePath);
            if (!dirs.exists())
                dirs.mkdirs();

            File file = new File(dirs, imageName);
            if (!file.exists()) {
                try {
                    //在指定的文件夹中创建文件
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (Exception e) {
                }
            }
            return file;
        } else {
            return null;
        }

    }

    public static String getOutFilePath(String filePath) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用
            File dirs = new File(Environment.getExternalStorageDirectory(),
                    filePath);
            if (!dirs.exists())
                dirs.mkdirs();
            return dirs.getAbsolutePath();
        } else {
            return "";
        }
    }

}
