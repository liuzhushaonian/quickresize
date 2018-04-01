# QuickResize 

——基于Java8以及JavaFX写的图片压缩器

**Java也能干大事系列！**

# 下载链接：

[百毒云](https://pan.baidu.com/s/1qiDQmEJrCQnyX-upfmH7zw)

密码: fph5

# 功能列表：
- **图片压缩！** 支持图片大小缩小与质量压缩，减小图片体积再也不成问题！
- **旋转图片！** 45°、90°、180°、360°随你转动，让你的图片秀出花来！
- **翻转图片！** 我就知道旋转图片也无法满足你，所以上下翻转或是左右翻转功能也一并给你,这下你的图片岂不是要翻出花来。
- **添加水印！** 翻出花来的图片都不如你的水印好看！所以我还为你提供了添加水印操作，支持图片水印与自定义文字水印，另外还有9个位置提供你放置水印喔（笑~）
- **格式转换！** 如此简单的功能我就不吹了。

# 使用方法：

在软件界面设置好参数后，将图片拖入到软件窗口即可，注意，其他的不配置也没关系，但一定要配置输出路径！

# 支持平台：

Windows、Linux、Mac或其他拥有JDK8环境的平台，移动平台（Android端）暂无打算


# 技术

没啥技术可言，就是用了个线程池，避免大量图片处理不过来，有兴趣看源码。

# 编译&打包

本项目基于maven，故可使用maven进行打包，我也有使用maven打的可执行jar文件，双击即可使用软件，但前提是有JRE环境。

maven打包方法：

```
mvn package assembly:single 
```  

当然，本应用主要还是FX，IDEA提供了很方便的打包方式。

在IDEA界面，file—Project Structure—Artifacts，之后流程如下

![alt](https://github.com/liuzhushaonian/quickresize/blob/master/screen_short/QQ20180401-224223.png)

![image](https://github.com/liuzhushaonian/quickresize/blob/master/screen_short/QQ20180401-224332.png)

![image](https://github.com/liuzhushaonian/quickresize/blob/master/screen_short/QQ20180401-224650.png)

![image](https://github.com/liuzhushaonian/quickresize/blob/master/screen_short/QQ20180401-224719.png)

![image](https://github.com/liuzhushaonian/quickresize/blob/master/screen_short/QQ20180401-224731.png)

![image](https://github.com/liuzhushaonian/quickresize/blob/master/screen_short/QQ20180401-224855.png)

总之没啥难度，就是麻烦，尤其在图标这一块，win那边还没适配好图标呢。

# 感谢

感谢以下开源框架：

[thumbnailator](https://github.com/coobird/thumbnailator)