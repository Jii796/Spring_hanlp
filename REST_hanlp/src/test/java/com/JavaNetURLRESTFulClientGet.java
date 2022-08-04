package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class JavaNetURLRESTFulClientGet {

    private static String targetURL = "http://localhost:8080/textClassify/";
    private static final Logger logger = Logger.getLogger(JavaNetURLRESTFulClientGet.class.getName());
    public static void main(String[] args) {

        try {

            String text= URLEncoder.encode("安全生产","UTF-8");
            targetURL=targetURL.concat(text);
            targetURL=targetURL.concat("/YOURTOKEN");
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
            logger.info("Output from Server:  \n");

            while ((output = responseBuffer.readLine()) != null) {
                output= URLDecoder.decode(output,"UTF-8");
                logger.info(output);
            }

            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
