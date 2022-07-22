package com.hankcs.hanlp;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.utility.TestUtility;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 第一个demo,演示文本分类最基本的调用方式
 *
 * @author hankcs
 */
public class Demo_for_test
{
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    public static final String CORPUS_FOLDER = TestUtility.ensureTestData("搜狗文本分类语料库迷你版", "http://file.hankcs.com/corpus/sogou-text-classification-corpus-mini.zip");
    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "data/test/classification-model_for6.ser";

    public static final String IN_FILE="D:\\holiday\\nlp\\Hanlp\\test.txt";

    public static void main(String[] args) throws IOException
    {
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());

        String[] filepath=getFilePath("D:\\holiday\\nlp\\Hanlp\\test\\");
        for(int i=0;i<size(filepath);i++){
            predict(classifier,getContext(filepath[i]));
        }
        //predict(classifier,getContext(IN_FILE));
        predict(classifier, "某有限公司一名工人在作业工程中不幸遇难");
        predict(classifier,"东川区应急管理局截至目前未接到安全生产类和自然灾害类的情况报告。");
        predict(classifier,"安全生产");
    }

    public static String[] forController(String text,int num ,boolean flag) throws IOException {
        IClassifier classifier =new NaiveBayesClassifier(trainOrLoadModel());
        String[] result=predictController(classifier,text,num,flag);
        return result;
    }
    public static String[] predictController(IClassifier classifier, String text,int num,boolean flag){
        String[] result=classifier.classify(text,num,flag);
        return result;
    }
    private static void predict(IClassifier classifier, String text)
    {
        /*String result=classifier.classify(text);
        System.out.printf("《%s》 属于分类 【%s】\n", text,result);*/
        String[] result=classifier.classify(text,3,true);
        System.out.printf("《%s》 属于分类 \n【", text);
        for(int i=0;i< size(result);i++){
            System.out.printf("%s,",result[i]);
        }
        System.out.printf("】\n");
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
    public static String[] getFilePath(String path) throws FileNotFoundException, IOException {
        String[] result = new String[20];
        File file = new File(path);
        if (file.isDirectory()) {
            System.out.println("文件夹");
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                result[i]=path+"\\"+filelist[i];
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
            e.printStackTrace();
        }

        String result=list.toString();
        return result;
    }
    private static NaiveBayesModel trainOrLoadModel() throws IOException
    {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}