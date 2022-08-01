package com.hankcs.hanlp;

import com.hankcs.hanlp.suggest.Suggester;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本推荐(句子级别，从一系列句子中挑出与输入句子最相似的那一个)
 * @author hankcs
 */
public class TextSuggester
{
    private static String filepath="data/test/hanlp_rest_data-master/安全生产";
    private static String filepath2="data/test/hanlp_rest_data-master/社会安全";
    private static String filepath3="data/test/hanlp_rest_data-master/自然灾害";
    private static String filepath4="data/test/hanlp_rest_data-master/公共卫生";
    private static String filepath5="data/test/hanlp_rest_data-master/通知";
    private static String filepath6="data/test/hanlp_rest_data-master/其他";
    public static void main(String[] args) throws IOException {
        Suggester suggester = new Suggester();
        String[] paths=getFilePath(filepath);

        for(String file:paths){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        /*String[] titleArray =
        (
                "威廉王子发表演说 呼吁保护野生动物\n" +
                "魅惑天后许佳慧不爱“预谋” 独唱《许某某》\n" +
                "《时代》年度人物最终入围名单出炉 普京马云入选\n" +
                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
                "日本保密法将正式生效 日媒指其损害国民知情权\n" +
                "英报告说空气污染带来“公共健康危机”"
        ).split("\\n");
        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }*/

        /*System.out.println(suggester.suggest("陈述", 2));       // 语义
        System.out.println(suggester.suggest("危机公关", 1));   // 字符
        System.out.println(suggester.suggest("mayun", 1));      // 拼音*/
        String text="太和街道辖区双龙商场旁环城南路段的中石化加油站疑似有渗水、塌方的情况汇报 (初报)\n" +
            "　　2020年8月1日01时45分许，我局接市应急局通报：在双龙商场旁环城南路段的中石化加油站疑似有渗水、塌方的情况。\n" +
            "接报后，我局立即向公安、街道及加油站负责人了解情况。经了解，系加油站在进行防渗改造（将单层油罐改造为双层），挖了一个约4.5米深的基坑，现正进行基坑底部混凝土浇筑，附近居民反映噪音过大及存在基坑塌方安全隐患。\n" +
            "据加油站负责人反馈，基坑已做槽钢支护，周围防护措施已做好，因夜间施工扰民已和周边小区物管协商。     \n" +
            "现我局已协调相关部门前往调查处置，后续情况待报。";
        System.out.println(suggester.suggest(text, 3).get(1));      // 拼音
    }
    public static Suggester TextSuggesterController() throws IOException {
        Suggester suggester=new Suggester();
        String[] paths1=getFilePath(filepath);
        for(String file:paths1){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        String[] paths2=getFilePath(filepath2);
        for(String file:paths2){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        String[] paths3=getFilePath(filepath3);
        for(String file:paths3){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        String[] paths4=getFilePath(filepath4);
        for(String file:paths4){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        String[] paths5=getFilePath(filepath5);
        for(String file:paths5){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        String[] paths6=getFilePath(filepath6);
        for(String file:paths6){
            if(file!=null)
                suggester.addSentence(getContext(file));
        }
        return suggester;
    }
    public static String SuggestText(Suggester suggester,String keyword){
        return suggester.suggest(keyword,1).get(0);
    }
    public static String[] getFilePath(String path) throws FileNotFoundException, IOException {
        String[] result = new String[3000];
        File file = new File(path);
        if (file.isDirectory()) {
            System.out.println("文件夹"+path);
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                result[i]=path+"/"+filelist[i];
            }
        }
        return result;
    }
    public static String getContext(String path){
        List<String> list = new ArrayList<String>();
        try
        {
            String encoding = "utf-8";
            File file = new File(path);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            System.out.println(path);
            e.printStackTrace();
        }

        String result=list.toString();
        return result;
    }
}