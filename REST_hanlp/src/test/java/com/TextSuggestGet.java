package com;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextSuggestGet {

    private static String targetURL = "http://localhost:8080/textSuggest/";

    public static String filepath="D:\\holiday\\nlp\\Hanlp\\test\\1.txt";

    public static void main(String[] args) {

        try {

            String text=getContext(filepath);
            text=URLEncoder.encode(text,"UTF-8");
            //String text= URLEncoder.encode("安全生产","UTF-8");
            targetURL=targetURL.concat(text);
            //java.net.URLEncoder.encode(targetURL, "utf-8");
            URL restServiceURL = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                    + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                (httpConnection.getInputStream())));

            String output;
            System.out.println("Output from Server:  \n");


            while ((output = responseBuffer.readLine()) != null) {
                output= URLDecoder.decode(output,"UTF-8");
                //System.out.println(output instanceof String);
                /*String[] result=output.split("\\[");
                for(int i=0;i<result.length;i++){
                    String[] re=result[i].split("]");
                    for(int j=0;j<re.length;j++){
                        String[] r=re[j].split("\"");
                        for(int k=0;k<r.length;k++){
                            System.out.println(r[k]);
                        }
                    }
                }*/
                /*String[] result=output.split(",\"1\",");
                for(int k=0;k<result.length;k++){
                    System.out.println(result[k]);
                }*/
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
