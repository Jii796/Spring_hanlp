package com;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JavaNetURLRESTFulClientPost {

    private static String targetURL = "http://localhost:8080/textClassify?text=";

    private static final Logger logger = Logger.getLogger(JavaNetURLRESTFulClientPost.class.getName());
    public static String filepath="yourpath";
    public static void main(String[] args) {
        try {
            String text=getContext(filepath);
            text=URLEncoder.encode(text,"utf-8");
            targetURL=targetURL.concat(text);
            targetURL=targetURL.concat("&accessToken=YOURTOKEN");
            URL targetUrl = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConnection.setRequestProperty("Accept-Charset", "utf-8");


            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                (httpConnection.getInputStream())));

            String output;
            logger.info("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                output= URLDecoder.decode(output,"utf-8");
                logger.info(output);
            }
            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getContext(String path){
        List<String> list = new ArrayList<>();
        try
        {
            String encoding = "utf-8";
            File file = new File(path);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                    Files.newInputStream(file.toPath()), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                logger.info("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            logger.info("读取文件内容出错");
            e.printStackTrace();
        }

        return list.toString();
    }
}
