package com;




//import com.github.junrar.Archive;
//import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;

public class unZipFile{

    public static void unZip(File zipFile, String outDir) throws IOException {

        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }

        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration enumeration = zip.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        }
    }
    public static void main(String[] args){
        String originDir = "D:\\holiday\\nlp\\REST_hanlp\\data\\test\\";
        String zipPath = originDir + "anlp_rest_data-master.zi";
        File zipFile = new File(zipPath);
        String rarPath = originDir + "new.rar";
        File rarFile = new File(rarPath);

        try {
            unZip(zipFile, "D:\\JavaPro\\TestFile\\Test\\");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


