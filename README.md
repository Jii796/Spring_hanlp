## 第三周小结，本周总目标：实现Hanlp框架和Spring框架的融合
### 星期一
- 开始了解Spring框架，刚开始由于零基础，先了解了Sping boot框架的搭建，并且动手写了一个helloworld的显示；
- 之后下午开始了解Spring REST Docs，但是发现这个框架是用来写相应的API文档的，因此，开始考虑Spring MVC和Spring REST这两种框架，在网上找到一些资源开始搭建。但是由于网上的框架使用的都是旧版本的IDEA，而自己电脑上下载的是最新版本的IDEA，导致在配置环境的时候出现了很多问题，当天没有解决。
### 星期二
- 上午先调试昨天未搭建完成的框架，发现是自己的某个配置文件出现错误，其中文件的路径写错了。
- 改正之后开始思考我该如何调用我自己的函数，有以下三个步骤：
1. 首先我就是需要通过网页内输入的内容来获取输入的参数（也就是将要分类的文本），
2. 之后将该文本作为参数调用Hanlp进行文本分类，
3. 最后再将文本分类的运行结果再传入客户端。
#### 如何获得jsp网页内的内容（第一步）
- 根据这个思路，开始思考如何获取jsp文件内的内容，首先先是在网上找到jsp和xml的教程，初步运用他们的语法，接着找到的是通过地址栏来获取（get）参数，但是这种方法不符合我的要求
- 之后想到用一个文本框来获取（POST），但是也就是在这里耗费了很久的时间：在修改框架的时候，不小心修改了pom文件中的一个依赖文件，导致一直编译报错，也花了很久的时间才发现错误的原因。
- 修改完BUG之后开始重新考虑该怎么获得jsp网页内的内容，这次打算通过HttpServletRequest来获取文本框中的内容，但是在通过println想要观察输入内容时却发现是一堆乱码，这个时候一直没有想到是因为自己web文件中未考虑中文的问题，还是自己不小心输入了一堆英文，发现可以正常显示，才明白自己的错误地方，解决代码如下所示。

```
<!--filter过滤器  解决中文乱码问题-->
    <filter>
        <filter-name>characterEncoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```
#### jsp页面的切换
- 由于自己的函数调用的时候包含结果的展示，还需要自己了解页面之间的切换。之后开始思考两个jsp页面之间的切换。
- 首先了解的是通过一个按钮来切换页面
```
<input type="button" onclick="window.location.href='register.jsp';" value="注册VIP">
```
该语句正常情况下可以将当前页面切换为“register.jsp”，但是出了bug，暂时放弃这个思路。
- 接着了解到切换默认页面
如下语句可以正确切换
`<jsp:forward page="/WEB-INF/jsp/save.jsp"--><!--/jsp:forward>`
而下面这行代码运行则会报错
`<% pageContext.forward("/WEB-INF/pages/Login.jsp"); %>`
但是这种切换页面的方式和自己的需求不符合
- 接着开始修改按钮的BUG，修改完成，发现可以切换页面，刚开始出错的原因有两个，一个是onclick的地址出现错误，一个是在controller中没有补全相应的函数。
#### 接着开始考虑如何将参数再次传入客户端（第三步）
这个在传参数的过程中，发现在jsp文件中
`<p>姓名 ${firstName}</p>`
这样的语句不能传参数，最后发现是jsp文件参数配置的问题，将jsp文件的头部修改如下：

```
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
```
这个也已经成功实现；之后就是将两个框架的融合（Hanlp和Spring MVC）,这个留作明天上午的任务。
### 星期三
#### 将该文本作为参数调用Hanlp进行文本分类（第二步）
- 这个是昨天下午遗留下来的任务：由于Hanlp框架是一个maven项目，而Spring MVC是一个web项目，因此在刚开始准备融合的时候出现了问题，因为Hanlp中，所有的展示函数都是在Test文件夹下实现的，但是对于Spring MVC中，我的Controller是在main文件夹下实现的，并且没有办法调用test文件夹下面的函数，这个时候尝试将Hanlp中文本分类需要用到的函数转移到main文件夹下，发现项目依然可以正确执行。这时候，将MVC框架下的jsp文件和xml文件都复制到main文件夹下相应的位置，并且在pom文件中添加相应的依赖项，开始编译。
- 刚开始编译的时候，会进行报错，提示gradle的版本不正确，这时候在官网上查找最新的版本，修改下载路径上的版本即可
- 然后修改了Controller函数，准备运行该函数，但是会出现如下所示的报错信息：
`错误: 无法初始化主类 com.hankcs.hanlp.DemoController 原因: java.lang.NoClassDefFoundError: javax/servlet/http/HttpServletRequest`
而这个报错的原因和昨天上午类似，如下所示：
```
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.4</version>
            <scope>provided</scope>
        </dependency>
```
昨天上午，是因为没有添加`<scope>provided</scope>`这句代码，导致编译报错，而今天也是在这个地方出错，因为缺少相应的包。
- 如果不去理会上面的错误，可以直接Tomcat run，这样项目也可以运行成功。这样第二步就基本上完成
#### 下午的任务：准备完善该接口的API文档，以及界面的优化以及补充
- 下午的时候，由于不小心删除了一整个module文件夹，没有办法恢复，导致框架运行的时候出错，因此又花费了一个小时左右的时间重新搭建框架。
在框架重新搭建成功之后，发现错误的原因，因为我会在网页中实时获取一个文本框内输入的内容，并将其作为参数调用文本分类的函数，这也就导致了当文本框内没有内容的时候，参数为null，这会使调用过程报错，因此添加一个判断条件，只有当文本框内的内容不为空的时候，才能调用文本分类的函数。
- 下面进行调用界面的美化，以及API文档的补充。
Api文档中主要是介绍函数的名称，作用、参数的名称、作用等。下午的大多数时间用来了解xml和jsp的语法。
### 星期四
今天的任务主要有两方面，一个是尝试将昨天的代码打包成war文件，并试着运行；另一个就是尝试使用Spring MVC框架搭建一个云接口。
#### 将代码打包成war包，并运行
由于昨天下午的时候尝试过在其他机器访问这个框架，但是访问不成功，今天上午的时候就一直尝试着将代码打包为war包，但是在运行的时候一直出错，报错信息如下：
```
 c.example.spring_for_mvc.DemoController  : No active profile set, falling back to 1 default profile: "default"
```
根据网上的经验，添加依赖，修改配置文件等方法都没有解决这个问题，最后尝试将代码转移到另一台机器上运行，这时候发现可以通过其他机器访问这个框架，但是对于war包依然会有错误。
### 星期五
昨天已经基本上把框架搭建完成，今天主要是做一些锦上添花的工作；
将jsp界面的切换改成了jsp页面的嵌套展示,最后页面展示如下：
![输入图片说明](https://images.gitee.com/uploads/images/2022/0722/173803_3da9d6bc_5361430.png "屏幕截图(17).png")
