package com.hankcs.hanlp;

import com.hankcs.hanlp.suggest.Suggester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author CAKJ
 */
@RestController
@SpringBootApplication
public class Controller {

    private static final String[] TYPE = {"first-type", "second-type", "third-type", "forth-type", "fifth-type", "sixth-type"};
    private static final String[] TYPE_POSSIBILITY = {"first-type-possibility", "second-type-possibility", "third-type-possibility",
        "forth-type-possibility", "fifth-type-possibility", "sixth-type-possibility"};
    private static Suggester suggester;

    private static final String UNKNOWN = "unknown";
    private static final String ERROR = "error";
    private static final String TOKEN = "Token";
    private static final String ACCESSTOKEN = "TEXTCLASSIFYFORHANLP";
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    /**
     * POST请求
     *
     * @param request     这是接收请求报文
     * @param text        这是接受到的参数
     * @param accessToken 接收到的参数
     * @return 返回一个map类型的结果，存放类型和类型的可能性
     * @throws IOException 抛出异常
     */
    @PostMapping(value = "/textClassify")
    public Map<String, String> textClassifyControllerPost(HttpServletRequest request, String text, String accessToken) throws IOException {

        Map<String, String> map = new HashMap<>(10);
        if (text != null && accessToken.equals(ACCESSTOKEN)) {
            map.put(TOKEN, "OK");
            text = URLDecoder.decode(text, "UTF-8");
            getRequestHeaders(request);
            Map<String, Map<String, String[]>> textMap = TextClassify.textClassifyController(text);
            String[] re = textMap.get("map1").get("label");
            String[] re2 = textMap.get("map2").get("possibility");
            logger.info(text);
            int i = size(re);
            //MultiValueMap map2 = new LinkedMultiValueMap();这个是之前使用的返回类型，但是在客户端获取的时候会出现中括号，因此改为了直接使用Map
            map.put(TYPE[0], stringToInt(re[0]));
            map.put(TYPE_POSSIBILITY[0], re2[0]);
            for (int j = 1; j < i; j++) {
                map.put(TYPE[j], stringToInt(re[j]));
                map.put(TYPE_POSSIBILITY[j], re2[j]);
            }
        } else if (accessToken == null) {
            map.put(TOKEN, "null");
        } else if (!accessToken.equals(ACCESSTOKEN)) {
            map.put(TOKEN, ERROR);
        } else {
            map.put("status", ERROR);
        }
        return map;
    }

    /**
     * get
     *
     * @param request     接受到的请求报文
     * @param text        接受到的参数
     * @param accessToken 接收到的参数
     * @return 返回一个map类型，存放的是类型和类型的可能性
     * @throws IOException 抛出异常
     */
    @ResponseBody
    @GetMapping(path = "/textClassify/{text}/{accessToken}")
    public Map<String, String> textClassifyControllerGet(HttpServletRequest request,
                                                         @PathVariable(value = "text") String text,
                                                         @PathVariable(value = "accessToken") String accessToken) throws IOException {
        Map<String, String> map = new HashMap<>(10);
        if (text != null && accessToken.equals(ACCESSTOKEN)) {
            getRequestHeaders(request);
            Map<String, Map<String, String[]>> textMap = TextClassify.textClassifyController(text);
            String[] re = textMap.get("map1").get("label");
            String[] re2 = textMap.get("map2").get("possibility");

            logger.info(text);
            int i = size(re);
            map.put(TYPE[0], stringToInt(re[0]));
            map.put(TYPE_POSSIBILITY[0], re2[0]);
            for (int j = 1; j < i; j++) {
                map.put(TYPE[j], stringToInt(re[j]));
                map.put(TYPE_POSSIBILITY[j], re2[j]);
            }
        } else if (accessToken == null) {
            map.put(TOKEN, "null");
        } else if (!accessToken.equals(ACCESSTOKEN)) {
            map.put(TOKEN, ERROR);
        } else {
            map.put("status", ERROR);
        }
        return map;
    }

    /**
     * 通过该方法获得输入文本的推荐文本
     * get
     *
     * @param keyword 输入的参数
     * @return 返回一个map，存放的是关键字以及和关键字匹配的一段文本
     */
    @GetMapping(path = "/textSuggest/{text}")
    public Map<String, String> textSuggestController(@PathVariable(value = "text") String keyword) {
        Map<String, String> map = new HashMap<>(10);
        String suggestResult = suggester.suggest(keyword, 1).get(0);
        map.put("keyword", keyword);
        map.put("similar text", suggestResult);
        return map;
    }

    /**
     * 该方法的功能为获取一个String类型的数组中实际元素的个数
     *
     * @param list 输入的参数列表
     * @return 返回列表的实际元素的个数
     */
    public int size(String[] list) {
        int length = 0;
        for (String s : list) {
            if (s != null) {
                length += 1;
            }
        }
        return length;
    }

    public int size(Double[] list) {
        int length = 0;
        for (Double aDouble : list) {
            if (aDouble != null) {
                length += 1;
            }
        }
        return length;
    }

    public static String stringToInt(String type) {
        switch (type) {
            case "安全生产":
                return "0001";
            case "社会安全":
                return "0002";
            case "自然灾害":
                return "0003";
            case "公共卫生":
                return "0004";
            case "通知":
                return "0005";
            case "其他":
                return "0006";
            default:
                return "0000";
        }
    }


    /**
     * @param request 获得的请求报文头部
     */
    private static void getRequestHeaders(HttpServletRequest request) {
        logger.info("Request Headers:");
        logger.info(request.getMethod());
        logger.info("IP:" + getIpAddr(request));
        logger.info("Host:" + request.getRemoteHost());
        logger.info("Port:" + request.getRemotePort());
        logger.info("Referer:" + request.getRequestURL());
        logger.info("Content-Type:" + request.getContentType());
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }
}
