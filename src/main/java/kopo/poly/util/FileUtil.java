package kopo.poly.util;

import java.io.File;

public class FileUtil {

    public static String mkdirForDate(String uploadDir) {

        String path = uploadDir + DateUtil.getDateTime("/yyyy/MM/dd");

        File Folder = new File(path);

        // 저장하기 위한 폴더가 존재하지 않으면, 폴더 생성
        if (!Folder.exists()) {
            Folder.mkdirs();
        }
        return path;
    }
}
