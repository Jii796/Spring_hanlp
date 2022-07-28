import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ClientGetSuggester {
    @Test
    public void restTemplateGetTest() throws UnsupportedEncodingException, JSONException {
        String keyword="森林大火";
        //text= URLEncoder.encode(text,"UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        String string = restTemplate.getForObject("http://172.18.8.43:18101/textSuggest/{1}"
                , String.class,keyword);
        System.out.println(string);
        JSONObject json=new JSONObject(string);
        System.out.println(json);
    }
}
