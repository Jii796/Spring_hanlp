package com.hankcs.hanlp;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.utility.TestUtility;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 该类用来进行文本分类，其中包括TextClassifyController和predictController两个方法为服务器端调用
 *
 * @author hankcs
 */
public class TextClassify
{
    private static final Logger logger = Logger.getLogger(TextClassify.class.getName());
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    public static final String CORPUS_FOLDER = TestUtility.ensureTestData("hanlp_rest_data-model", "https://codeload.github.com/Jii796/hanlp_rest_data/zip/refs/heads/model");
    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "data/test/hanlp_rest_data-model/classification-model_for.ser";


    public static void main(String[] args) throws IOException
    {
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());

        String[] filepath=getFilePath("YOUR_PATH");
        for(int i=0;i<size(filepath);i++){
            predict(classifier,getContext(filepath[i]));
        }
        predict(classifier, "某有限公司一名工人在作业工程中不幸遇难");
        predict(classifier,"东川区应急管理局截至目前未接到安全生产类和自然灾害类的情况报告。");
        predict(classifier,"安全生产");
        Map<String,Map<String,String[]>> result=classifier.classify("安全生产",3,true,1);
        String[] r1= (String[]) result.get("map2").get("possibility");
    }

    public static Map<String,Map<String,String[]>> textClassifyController(String text) throws IOException {
        IClassifier classifier =new NaiveBayesClassifier(trainOrLoadModel());
        return predictController(classifier,text);
    }
    public static Map<String, Map<String,String[]>> predictController(IClassifier classifier, String text){
        return classifier.classify(text,3,true,1);
    }
    private static void predict(IClassifier classifier, String text)
    {
        String[] result=classifier.classify(text,3,true);
        logger.info("《%s》 属于分类 \n【"+text);
        for(int i=0;i< size(result);i++){
            logger.info("%s,"+result[i]);
        }
        logger.info("】\n");
    }
    private static int size(String[] list){
        int result=0;
        for(int i=0;i<list.length;i++){
            if(list[i]!=null){
                result+=1;
            }
        }
        return result;
    }
    private static int size(Double[] list){
        int result=0;
        for(int i=0;i<list.length;i++){
            if(list[i]!=null){
                result+=1;
            }
        }
        return result;
    }
    public static String[] getFilePath(String path) {
        String[] result = new String[20];
        File file = new File(path);
        if (file.isDirectory()) {
            logger.info("文件夹");
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                result[i]=path+"\\"+filelist[i];
            }
        }
        return result;
    }
    public static String getContext(String path){
        List<String> list = new ArrayList<>();
        String encoding = "utf-8";
        try
        {
            File file = new File(path);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                try (InputStreamReader read = new InputStreamReader(
                    Files.newInputStream(file.toPath()), encoding)) {
                    // 考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;

                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        list.add(lineTxt);
                    }
                    bufferedReader.close();
                }
            }
            else
            {
                logger.info("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            logger.info("读取文件内容出错");
            e.printStackTrace();
        }

        return list.toString();

    }
    private static NaiveBayesModel trainOrLoadModel() throws IOException
    {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) {
            return model;
        }


        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            logger.info("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }
         // 创建分类器，更高级的功能请参考IClassifier的接口定义
        IClassifier classifier = new NaiveBayesClassifier();
        // 训练后的模型支持持久化，下次就不必训练了
        classifier.train(CORPUS_FOLDER);
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}
