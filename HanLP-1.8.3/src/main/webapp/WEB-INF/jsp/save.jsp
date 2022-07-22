<%@  page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>预测结果显示</title>
</head>
<body bgcolor="#f0f8ff">
<p>当前时间是 <%=(new java.util.Date()).toLocaleString()%></p>
<P>将要分类的文本内容为：</P>
<P><textarea name="预测内容" cols="100" rows="3" align="center">${TextToClassify}</textarea></P>
<P>预测的分类结果是 ${result}</P>
<P><input type="text" name="预测结果" value=${result} /></P>
<p>${show}</p>
</body>
</html>