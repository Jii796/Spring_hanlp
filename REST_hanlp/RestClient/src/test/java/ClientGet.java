import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ClientGet {
    /**
     * get请求
     * @throws UnsupportedEncodingException
     */
    @Test
    public void restTemplateGetTest() throws UnsupportedEncodingException {
        String url="http://172.18.8.43:18101/textClassify/{1}";
        String url2="http://localhost:8080/textClassify/{1}";
        String text="东川区应急管理局截至目前未接到安全生产类和自然灾害类的情况报告。";
        //text= URLEncoder.encode(text,"UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        String string = restTemplate.getForObject(url2
                , String.class,text);
        System.out.println(URLDecoder.decode(string,"UTF-8"));
    }
}