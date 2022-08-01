package com.hankcs.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankKeyword;

import java.util.List;

/**
 * 关键词提取
 * @author hankcs
 */
public class TextKeyword
{
    public static void main(String[] args)
    {
        String content = "太和街道辖区双龙商场旁环城南路段的中石化加油站疑似有渗水、塌方的情况汇报 (初报)\n" +
            "　　2020年8月1日01时45分许，我局接市应急局通报：在双龙商场旁环城南路段的中石化加油站疑似有渗水、塌方的情况。\n" +
            "接报后，我局立即向公安、街道及加油站负责人了解情况。经了解，系加油站在进行防渗改造（将单层油罐改造为双层），挖了一个约4.5米深的基坑，现正进行基坑底部混凝土浇筑，附近居民反映噪音过大及存在基坑塌方安全隐患。\n" +
            "据加油站负责人反馈，基坑已做槽钢支护，周围防护措施已做好，因夜间施工扰民已和周边小区物管协商。     \n" +
            "现我局已协调相关部门前往调查处置，后续情况待报。";
        List<String> keywordList = HanLP.extractKeyword(content, 3);
        System.out.println(keywordList);
    }
}
