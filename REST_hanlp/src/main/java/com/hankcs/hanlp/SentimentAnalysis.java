package com.hankcs.hanlp;


import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.utility.TestUtility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 第一个demo,演示文本分类最基本的调用方式
 *
 * @author hankcs
 */
public class SentimentAnalysis {
    private static final Logger logger = Logger.getLogger(SentimentAnalysis.class.getName());

    public static final String CORPUS_FOLDER = TestUtility.ensureTestData("ChnSentiCorp情感分析酒店评论", "http://file.hankcs.com/corpus/ChnSentiCorp.zip");

    public static final String MODEL_PATH = "data/test/classification-model-sa.ser";

    public static IClassifier classifier;
    public static void main(String[] args) throws IOException {
        classifier = new NaiveBayesClassifier(trainOrLoadModel());
        //classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        predict(classifier, "前台客房服务态度非常好！早餐很丰富，房价很干净。再接再厉！");
        predict(classifier, "结果大失所望，灯光昏暗，空间极其狭小，床垫质量恶劣，房间还伴着一股霉味。");
        predict(classifier, "可利用文本分类实现情感分析，效果还行");
    }

    private static void predict(IClassifier classifier, String text) {
        String result = classifier.classify(text);
        logger.info(text);
        logger.info(":情感极性为\n");
        logger.info(result);
    }

    public static String sentimentAnalysisController(String text) throws IOException {
        classifier = new NaiveBayesClassifier(trainOrLoadModel());
        return classifier.classify(text);
    }

    private static NaiveBayesModel trainOrLoadModel() throws IOException {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) {
            return model;
        }

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
            logger.info("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier();
        classifier.train(CORPUS_FOLDER);
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}