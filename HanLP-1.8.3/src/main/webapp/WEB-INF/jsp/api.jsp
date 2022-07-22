<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <title>api文档</title>
</head>
<body bgcolor="#f5fffa">
<font size="5"><strong>这是api文档</strong></font><br>
<br>
<font size="4"><strong>String[] forController(String text,int num,boolean flag) throws IOException</strong></font>
<br>
<br>
<font size="4">接口名称：<strong>forController</strong></font><br>
<br>
<font size="4">返回值：<strong>String[]</strong></font><br>
<br>
函数作用：接受客户端传来的参数，然后调用predictController这一函数，进行文本分类。<br>
<br>
参数情况<br>
<table border="1" bgcolor="#f0f8ff">

    <tr bgcolor="#f0ffff">
        <td>Parameter name</td>
        <td>Parameter type</td>
        <td>Is necessory</td>
        <td>Function of parameter</td>
        <td>remarks</td>
    </tr>
    <tr bgcolor="#f0f8ff">
        <td>text</td>
        <td>String</td>
        <td>yes</td>
        <td>该参数作为待分类的文本传入接口</td>
        <td>无默认值</td>
    </tr>
    <tr bgcolor="#f0ffff">
        <td>num</td>
        <td>int</td>
        <td>not</td>
        <td>该参数的作用是想要显示预测的文本有几个类型标签</td>
        <td>默认值为1，如果参数flag的值为true，那么该参数就可以忽略</td>
    </tr>
    <tr bgcolor="#f0f8ff">
        <td>flag</td>
        <td>boolean</td>
        <td>not</td>
        <td>该参数的作用是判断待预测文本有几个标签，如果为true，那么自动判断应该有几个标签，如果为false，那么显示num个标签</td>
        <td>默认值为true</td>
    </tr>
</table><br>
示例代码<br>
<br>
<font size="2">public static String[] forController(String text,int num,boolean flag) throws IOException {<br>
    IClassifier classifier =new NaiveBayesClassifier(trainOrLoadModel());<br>
    String[] result=predictController(classifier,text,num,flag);<br>
    return result;<br>
    }<br></font>

<font size="4"><strong>public static String[] predictController(IClassifier classifier, String text,int num,boolean flag)</strong></font>
<br>
<br>
<font size="4">接口名称：<strong>predictController</strong></font><br>

<br>
<font size="4">返回值：<strong>String[]</strong></font><br>

<br>
<font size="3">函数作用：接受forController传来的参数，并且调用classify函数（该方法不能直接调用）进行文本分类</font><br>

参数情况<br>
<br>
<table border="1" bgcolor="#f0f8ff">
    <tr bgcolor="#f0f8ff">

        <td>Parameter name</td>
        <td>Parameter type</td>
        <td>Function of parameter</td>
        <td>remarks</td>
    </tr>
    <tr bgcolor="#f0ffff">
        <td>classifier</td>
        <td>IClassifier</td>
        <td>通过该分类器调用classify方法，对文本进行分类</td>
        <td>该分类器的定义与初始化是在forController函数中完成</td>
    </tr>
    <tr bgcolor="#f0f8ff">
        <td>text</td>
        <td>String</td>
        <td></td>
        <td>该参数和forController中相同</td>
    </tr>
    <tr bgcolor="#f0ffff">
        <td>num</td>
        <td>int</td>
        <td></td>
        <td>该参数和forController中相同</td>
    </tr>
    <tr bgcolor="#f0f8ff">
        <td>flag</td>
        <td>boolean</td>
        <td></td>
        <td>该参数和forController中相同</td>
    </tr>
</table>
示例代码<br>
<br>
<font size="2">public static String[] predictController(IClassifier classifier, String text,int num,boolean flag){<br>
    String[] result=classifier.classify(text,num,flag);<br>
    return result;<br>
    }<br></font>

</body>
</html>
