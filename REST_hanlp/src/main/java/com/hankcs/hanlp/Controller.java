package com.hankcs.hanlp;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hankcs.hanlp.suggest.Suggester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class Controller {
    private static TextClassify textClassify=new TextClassify();

    public String result;


    public static final String[] type= {"first-type","second-type","third-type","forth-type","fifth-type","sixth-type"};
    public static final String[] type_prossibility= {"first-type-prossibility","second-type-prossibility","third-type-prossibility",
        "forth-type-prossibility","fifth-type-prossibility","sixth-type-prossibility"};
    public static final String[] textType={"安全生产","社会安全","自然灾害","公共卫生","通知","其他"};
    public static  Suggester suggester;

    //该代码块是为了初始化suggester，耗时较久，并且textSuggest不再继续使用，因此注释掉
    /*static {
        try {
            suggester = new TextSuggester().TextSuggesterController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    /**
     * POST请求
     * @param request
     * @param text
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/textClassify",method= RequestMethod.POST)
    public Map textClassifyControllerPost(HttpServletRequest request,String text) throws IOException {

        Map<String,String> map=new HashMap<>();
        if(text!=null){

            text=URLDecoder.decode(text,"UTF-8");
            this.getRequestHeaders(request);
            Map<String,Map> textMap=textClassify.textClassifyController(text,true,1);
            String[] re= (String[]) textMap.get("map1").get("label");
            Double[] re2=(Double[])textMap.get("map2").get("prossibility");
            System.out.println(text);
            int i=size(re);
            //MultiValueMap map2 = new LinkedMultiValueMap();这个是之前使用的返回类型，但是在客户端获取的时候会出现中括号，因此改为了直接使用Map
            map.put(type[0],StringToInt(re[0]));
            map.put(type_prossibility[0],re2[0].toString());

            for(int j=1;j<i;j++){

                map.put(type[j],StringToInt(re[j]));
                map.put(type_prossibility[j],re2[j].toString());
            }
            return map;
        }else{
            map.put("status","error");
            return map;
        }
    }
    /**
     * 通过该方法获得文本的分类类型
     * get
     * @param text
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/textClassify/{text}",method=RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Map TextClassifyControllerGet(HttpServletRequest request, @PathVariable(value = "text") String text) throws IOException {
        Map<String,String> map=new HashMap<>();
        if(text!=null){
            this.getRequestHeaders(request);
            Map<String,Map> textMap=textClassify.textClassifyController(text,true,1);
            String[] re= (String[]) textMap.get("map1").get("label");
            Double[] re2=(Double[]) textMap.get("map2").get("prossibility");
            System.out.println(text);
            int i=size(re);
            map.put(type[0],StringToInt(re[0]));
            map.put(type_prossibility[0],re2[0].toString());
            for(int j=1;j<i;j++){
                map.put(type[j],StringToInt(re[j]));
                map.put(type_prossibility[j],re2[j].toString());
            }
            return map;
        }else{
            map.put("status","error");
            return map;
        }
    }

    /**
     * 通过该方法获得输入文本的推荐文本
     * get
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/textSuggest/{text}",method = RequestMethod.GET)
    public MultiValueMap TextSuggestController(@PathVariable(value="text") String keyword) throws IOException {

        MultiValueMap map=new LinkedMultiValueMap();
        //List<String> keywordList = HanLP.extractKeyword(text, 5);
        String result=suggester.suggest(keyword,1).get(0);
        map.add("keyword",keyword);
        map.add("similar text",result);
        return map;
    }
    /**
     * 该方法的功能为获取一个String类型的数组中实际元素的个数
     * @param list
     * @return
     */
    public int size(String[] list){
        int length=0;
        for(int i=0;i<list.length;i++){
            if(list[i]!=null)
                length+=1;
        }
        return length;
    }
    public int size(Double[] list){
        int length=0;
        for(int i=0;i<list.length;i++){
            if(list[i]!=null)
                length+=1;
        }
        return length;
    }
    public static String StringToInt(String type){
        switch(type) {
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
        }
        return "0000";
    }


    /**
     *
     * @param request
     */
    public static void getRequestHeaders(HttpServletRequest request){
        System.out.println("Request Headers:");
        System.out.println(request.getMethod());
        System.out.println("Host:"+request.getRemoteHost());
        System.out.println("Port:"+request.getRemotePort());
        System.out.println("Referer:"+request.getRequestURL());
        System.out.println("Content-Type:"+request.getContentType());
    }
    public static void main(String[] args){
        SpringApplication.run(Controller.class, args);
    }
}
