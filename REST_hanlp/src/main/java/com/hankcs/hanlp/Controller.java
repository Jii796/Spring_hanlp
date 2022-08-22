import com.hankcs.hanlp.suggest.Suggester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
	@@ -21,14 +19,13 @@
@SpringBootApplication
public class Controller {

    private static final String[] TYPE = {"type", "main-type", "secondary-type3", "third-type", "forth-type", "fifth-type", "sixth-type"};
    private static final String[] TYPE_POSSIBILITY = {"type-possibility", "main-type-possibility", "secondary-type-possibility", "third-type-possibility",
        "forth-type-possibility", "fifth-type-possibility", "sixth-type-possibility"};
    private static Suggester suggester;

    private static final String UNKNOWN = "unknown";
    private static final String ERROR = "error";

    private static final String TOKEN = "Token";
    private static final String ACCESSTOKEN = "TEXTCLASSIFYFORHANLP";
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
	@@ -43,11 +40,11 @@ public class Controller {
     * @throws IOException 抛出异常
     */
    @PostMapping(value = "/textClassify")
    public MultiValueMap<String, String> textClassifyControllerPost(HttpServletRequest request, String text, String accessToken) throws IOException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        boolean flag = AccessToken.getToken(accessToken);
        if (text != null && flag) {
            map.add(TOKEN, "OK");
            text = URLDecoder.decode(text, "UTF-8");
            getRequestHeaders(request);
            Map<String, Map<String, String[]>> textMap = TextClassify.textClassifyController(text);
	@@ -56,18 +53,18 @@ public MultiValueMap<String, String> textClassifyControllerPost(HttpServletReque
            logger.info(text);
            int i = size(re);
            //MultiValueMap map2 = new LinkedMultiValueMap();这个是之前使用的返回类型，但是在客户端获取的时候会出现中括号，因此改为了直接使用Map
            map.add(TYPE[0], stringToInt(re[0]));
            map.add(TYPE_POSSIBILITY[0], re2[0]);
            for (int j = 1; j < i; j++) {
                map.add(TYPE[0], stringToInt(re[j]));
                map.add(TYPE_POSSIBILITY[0], re2[j]);
            }
        } else if (accessToken == null) {
            map.add(TOKEN, "null");
        } else if (!flag) {
            map.add(TOKEN, ERROR);
        } else {
            map.add("status", ERROR);
        }
        return map;
    }
	@@ -92,6 +89,7 @@ public Map<String, String> textClassifyControllerGet(HttpServletRequest request,
            Map<String, Map<String, String[]>> textMap = TextClassify.textClassifyController(text);
            String[] re = textMap.get("map1").get("label");
            String[] re2 = textMap.get("map2").get("possibility");
            logger.info(text);
            int i = size(re);
            map.put(TYPE[0], stringToInt(re[0]));
	@@ -126,6 +124,23 @@ public Map<String, String> textSuggestController(@PathVariable(value = "text") S
        return map;
    }

    /**
     *
     * @param text 传入的参数
     * @return 返回结果
     * @throws IOException 抛出异常
     */
    @PostMapping(path = "/SentimentAnalysis")
    public Map<String, String> sentimentAnalysisController(String text) throws IOException {
        Map<String, String> map = new HashMap<>(10);
        if (text != null) {
            String result = SentimentAnalysis.sentimentAnalysisController(text);
            map.put("result", result);
        } else {
            map.put(ERROR, "text is null");
        }
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
