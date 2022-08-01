package com;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class demo {
    /**
     * get请求
     * @throws UnsupportedEncodingException
     */
    @Test
    public void restTemplateGetTest() throws UnsupportedEncodingException, JSONException {
        String text="森林火情";
        //text= URLEncoder.encode(text,"UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        String string = restTemplate.getForObject("http://172.18.8.43:18101/textSuggest/{1}"
            , String.class,text);
        System.out.println(string);
        JSONObject json=new JSONObject(string);
        System.out.println(json);
    }
}
