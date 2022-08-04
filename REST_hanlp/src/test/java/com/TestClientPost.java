package com;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestClientPost {

    public static final String[] type= {"first-type","second-type","third-type","forth-type","fifth-type","sixth-type"};

    public static final String[] type_possibility= {"first-type-possibility","second-type-possibility","third-type-possibility",
        "forth-type-possibility","fifth-type-possibility","sixth-type-possibility"};

    private static final Logger logger = Logger.getLogger(TestClientPost.class.getName());

    private static final String path = System.getProperty("user.dir")+"\\src\\test\\java\\ClientPost.log";

    @Test
    public void rtPostObject() throws JSONException, IOException {

        FileHandler fileHandler = new FileHandler(path);
        RestTemplate restTemplate = new RestTemplate();
        String url="http://localhost:8080/textClassify";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language","zh");
        String request="森林火情";
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("text",request);
        map.add("accessToken","YOURTOKEN");

        assertNotNull(restTemplate.postForEntity(url, map, String.class));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, map, String.class);
        fileHandler.setFormatter(new SimpleFormatter());

        logger.addHandler(fileHandler);

        logger.log(Level.INFO,"Start Writing to File..");
        logger.info(getResponseHeaders(responseEntity));
        logger.info("Write Successfuly.");
    }
    public static String getResponseHeaders(ResponseEntity<String> responseEntity) throws JSONException {
        String ResponseHeaders = new String();
        ResponseHeaders=ResponseHeaders.concat("Response Headers:\n");
        ResponseHeaders=ResponseHeaders.concat(String.valueOf(responseEntity.getStatusCode())+"\n");
        ResponseHeaders=ResponseHeaders.concat("Content-Type:"+responseEntity.getHeaders().get("Content-Type")+"\n");
        ResponseHeaders=ResponseHeaders.concat("Transfer-Encoding:"+responseEntity.getHeaders().get("Transfer-Encoding")+"\n");
        ResponseHeaders=ResponseHeaders.concat("Date:"+responseEntity.getHeaders().get("Date")+"\n");
        ResponseHeaders=ResponseHeaders.concat("Keep-Alive:"+responseEntity.getHeaders().get("Keep-Alive")+"\n");
        ResponseHeaders=ResponseHeaders.concat("Connection:"+responseEntity.getHeaders().get("Connection")+"\n");
        String result=responseEntity.getBody();
        JSONObject json=new JSONObject(result);
        ResponseHeaders=ResponseHeaders.concat("response\n{\n");
        int flag=0;
        for(int i=0;i<6;i++){
            if(json.has(type[i])){
                ResponseHeaders=ResponseHeaders.concat("        \""+type[i]+"\":"+IntToString((String) json.get(type[i]))+"\n");
                ResponseHeaders=ResponseHeaders.concat("        \""+type_possibility[i]+"\":"+json.get(type_possibility[i])+"\n");
                flag=1;
            }
        }
        if(flag==0){
            ResponseHeaders=ResponseHeaders.concat("        accessToken:"+json.get("Token")+"\n");
        }
        ResponseHeaders=ResponseHeaders.concat("}\n");
        return ResponseHeaders;
    }

    public static String IntToString(String label){
        switch(label){
            case "0001":
                return "安全生产";
            case "0002":
                return "社会安全";
            case "0003":
                return "自然灾害";
            case "0004":
                return "公共卫生";
            case "0005":
                return "通知";
            case "0006":
                return "其他";
        }
        return "null";
    }
}
