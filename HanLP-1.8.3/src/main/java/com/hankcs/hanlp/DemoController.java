package com.hankcs.hanlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @author lyj
 * @Title: DemoController
 * @ProjectName springmvc-demo
 * @Description: TODO
 * @date 2020/6/9 21:21
 */

@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
     * http://localhost:8080/demo/handle01
     */

    public static Demo_for_test d=new Demo_for_test();
    public String result=null;
    public boolean flag=true;
    public int num=1;
    public String text;
    @RequestMapping("/main")
    public ModelAndView ShowMain(){
        ModelAndView modelAndView=new ModelAndView();
        //modelAndView.addObject("TextToClassify",s);
        //modelAndView.addObject("result",result);
        modelAndView.setViewName("main");
        return modelAndView;
    }
    @RequestMapping("/handle01")
    ModelAndView handle01(HttpServletRequest req) throws IOException {
        Date date=new Date();
        System.out.println(req);
        //result=null;
        String flagget=req.getParameter("flag");
        if(flagget!=null&&flagget.equals("false"))
            flag=false;
        System.out.println("flag="+flag);
        String Stringofnum=req.getParameter("num");
        int numget=0;
        if(Stringofnum!=null)
            num= Integer.parseInt(Stringofnum);
        System.out.println("num="+num);
        text=req.getParameter("TextToClassify");
        System.out.println("TextToClassify="+text);
        if(text!=null){
            String re[]=d.forController(text,num,flag);
            int i=size(re);
            System.out.println(i);
            result=re[0].concat(";");
            for(int j=1;j<i;j++){
                result=result.concat(re[j]).concat(";");
            }
            System.out.println(result);
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    @RequestMapping("/save")
    public ModelAndView Save(HttpServletRequest req){

        ModelAndView modelAndView=new ModelAndView();
        if(text!=null){
            modelAndView.addObject("TextToClassify",text);
            modelAndView.addObject("result",result);
            modelAndView.addObject("show","已经成功预测");
        }
        else{
            modelAndView.addObject("TextToClassify","请输入您想要分类的文本");
            modelAndView.addObject("result","待分类");
            modelAndView.addObject("show","预测还没有开始");
        }
        modelAndView.setViewName("save");
        return modelAndView;
    }
    @RequestMapping("/api")
    public ModelAndView Api(HttpServletRequest req){
        Date date=new Date();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("TextToClassify",text);
        modelAndView.addObject("result",result);
        modelAndView.setViewName("api");
        return modelAndView;
    }
    public int size(String[] list){
        int length=0;
        for(int i=0;i<list.length;i++){
            if(list[i]!=null)
                length+=1;
        }
        return length;
    }
    public static void main(String[] args){
        SpringApplication.run(DemoController.class, args);
    }



}
