package com;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class JavaNetURLRESTFulClientPost {

    private static String targetURL = "http://localhost:8080/textClassify?text=";

    public static String filepath="D:\\holiday\\nlp\\Hanlp\\test\\1.txt";
    public static void main(String[] args) {

        try {


            //String text= URLEncoder.encode("安全生产","UTF-8");
            String text=getContext(filepath);
            text=URLEncoder.encode(text,"utf-8");
            targetURL=targetURL.concat(text);
            //targetURL=targetURL.concat("&flag=true");
            URL targetUrl = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConnection.setRequestProperty("Accept-Charset", "utf-8");

            /*String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();*/

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                (httpConnection.getInputStream())));

            String output;
            System.out.println("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                output= URLDecoder.decode(output,"utf-8");
                System.out.println(output);
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
    public static String getContext(String path){
        List<String> list = new ArrayList<String>();
        try
        {
            String encoding = "utf-8";
            File file = new File(path);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        String result=list.toString();
        return result;
    }
}
