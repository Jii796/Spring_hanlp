<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--该语句可以改变默认文件的显示顺序-->
<!--jsp:forward page="/WEB-INF/jsp/save.jsp"--><!--/jsp:forward-->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>NLP_文本分类</title>
</head>
<body bgcolor="#fffff0">
<input type="button" onclick="window.location.href='api';" value="查看api">
<input type="button" onclick="window.location.href='save';" value="查看预测结果">
<p>
    当前时间是 <%=(new java.util.Date()).toLocaleString()%>
</p>
<form action="" method="post">
    选择参数flag的值（默认为true）
    <select class="ui-select" name="flag" id="select">
        <option selected="" value="true">true</option>
        <option value="false">flase</option>
    </select><br>
    选择参数num的值（默认为1）
    <select class="ui-select" name="num" id="num">
        <option selected="" value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
    </select><br>
    <input type="button" name="TextToClassify2" value="请输入你想要预测的文本" /><br>
    <textarea name="TextToClassify" cols="100" rows="3" align="center"></textarea><br>
    <input type="submit" value="开始预测" />
</form>
<!--
<input type="button" onclick="window.location.href='save';" value="查看预测结果">
-->
<iframe name="save" src="save" width="900" height="700" scrolling="auto"></iframe>
<iframe name="api" src="api" width="900" height="700" scrolling="auto"></iframe>
</body>
</html>