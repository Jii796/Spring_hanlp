## 项目介绍
该项目下一共包含了两个开源项目，第一个是Spring MVC框架和NLP框架的融合，第二个则是一个RESTful风格的NLP框架接口
### 项目一
该项目是一个web项目。
### 项目二
1. data文件夹。该文件夹下包含dictionary和model两个文件夹，这是自然语言处理的时候需要用到的模型文件和字典文件，在初次运行项目的时候会直接下载相应的模型文件。另外在下载该项目并本地运行之后，data文件夹下会多出来一个test/hanlp_rest_data-master文件夹，里面存放的是文本分类需要用到的训练数据，当然也可以直接本地下载训练数据然后解压放到项目文件夹下：[点击下载](https://codeload.github.com/Jii796/hanlp_rest_data/zip/refs/heads/master)，其中包含六种类型的训练数据。

2. src文件夹。该文件夹下包含main和test两个文件夹，其中模型的具体实现在main文件夹下，包括文本分类的实现（TextClassify.java）、接口的实现(Controller.java)等。而test文件夹下则是java编写的模拟客户端来测试相应的接口。包括四个文件：JavaNetURLRESTFulClientGet.java、JavaNetURLRESTFulClientPost.java、demoTemplateGet.java和demoTemplatePost.java。分别是java自带的HttpURLConnection编写的模拟GET和POST请求和restTemplate编写的模拟GET和POST请求。其中使用HttpURLConnection的时候需要调整Controller.java文件中的参数的读入格式和结果的返回格式（需要全部转换为UTF-8编码），而使用restTemplate则不需要修改Controller.java文件。

3. Controller.java文件。在该文件中主要实现了三个接口，两个是文本分类的接口（一个GET方法，一个POST方法），还有一个文本推荐的接口（GET方法）：
```
textClassifyControllerPost(HttpServletRequest request,String text,String accessToken)、
TextClassifyControllerGet(HttpServletRequest request, @PathVariable(value = "text") String text,@PathVariable(value="accessToken") String accessToken)
TextSuggestController(@PathVariable(value="keyword") String keyword)。
```
其中在文本分类的两个接口中除了获得参数文本之外，还将请求报文的内容在控制台显示出来，而文本推荐的接口（它所需要的参数是一个关键字）虽然已经实现，但是在内网服务器上运行的时候是将这一部分注释掉了。
- 在下载开源项目之后，右键执行TextClassify.java文件，下载相应的数据，之后再执行Controller.java文件，这时候可以通过test文件夹下的几个文件进行测试。 **注意：文件的编码格式一定要是UTF-8格式，否则会很容易出现中文乱码的问题。** 
