package com;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.logging.*;


import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestClientGet {

    private static final Logger logger = Logger.getLogger(TestClientGet.class.getName());
    private static final String path = System.getProperty("user.dir")+"\\src\\test\\java\\ClientGet.log";
    /**
     * get请求
     */
    @Test()
    void restTemplateGetTest() throws IOException {
        FileHandler fileHandler = new FileHandler(path);
        String url="http://localhost:8080/textClassify/{1}/{2}";
        String text="东川区应急管理局截至目前未接到安全生产类和自然灾害类的情况报告。";
        //text= URLEncoder.encode(text,"UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        assertNotNull(restTemplate.getForObject(url
            , String.class,text,"YOURTOKEN"));
        String string = restTemplate.getForObject(url
            , String.class,text,"YOURTOKRN");
        fileHandler.setFormatter(new SimpleFormatter());//官设格式

        logger.addHandler(fileHandler);

        logger.log(Level.INFO,"Start Writing to File..");
        logger.info(string);
        logger.info("Write Successfuly.");
    }
}