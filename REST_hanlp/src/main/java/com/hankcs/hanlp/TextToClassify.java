package com.hankcs.hanlp;

public class TextToClassify {
    private String text;
    private int num;
    private boolean flag;
    private String operation;
    public TextToClassify(){
        this.text="安全生产";
        this.num=1;
        this.flag=true;
        this.operation="classify";
    }
    public TextToClassify(String inputText){
        this.text=inputText;
        this.num=1;
        this.flag=true;
        this.operation="classify";
    }
    public TextToClassify(String inputText,boolean inputFlag,int inputNum){
        this.text=inputText;
        this.flag=inputFlag;
        this.num=inputNum;
        this.operation="calssify";
    }
}
