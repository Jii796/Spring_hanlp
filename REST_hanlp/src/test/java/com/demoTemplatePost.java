package com;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class demoTemplatePost {
    @Test
    public void rtPostObject() throws JSONException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://172.18.8.43:18101/textClassify";
        String url2="http://localhost:8080/textClassify";
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type","application/json");
        headers.add("Accept-Language","zh");
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String request="东川区应急管理局截至目前未接到安全生产类和自然灾害类的情况报告。";
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("text",request);

        //HashMap<String,String> test=new HashMap<>();

        //HttpEntity<Object> request2 = new HttpEntity<>((Object) map, headers);
        /*String response=restTemplate.postForObject(url,map,String.class);
        System.out.println(response);
        JSONObject jsonObject=new JSONObject(response);
        String t= jsonObject.getString("text");
        System.out.println(t);*/
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, map, String.class);
        getResponseHeaders(responseEntity);
    }
    public static void getResponseHeaders(ResponseEntity<String> responseEntity){
        System.out.println("Response Headers:");
        System.out.println(responseEntity.getStatusCode());
        System.out.println("Content-Type:"+responseEntity.getHeaders().get("Content-Type"));
        System.out.println("Transfer-Encoding:"+responseEntity.getHeaders().get("Transfer-Encoding"));
        System.out.println("Date:"+responseEntity.getHeaders().get("Date"));
        System.out.println("Keep-Alive:"+responseEntity.getHeaders().get("Keep-Alive"));
        System.out.println("Connection:"+responseEntity.getHeaders().get("Connection"));
        System.out.println(responseEntity.getBody());
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
