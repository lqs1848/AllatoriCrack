# AllatoriCrack
破解 Java 混淆工具 Allatori [官网](http://www.allatori.com/)

allatori的使用方法 官方的demo里都有 而且是 .bat 的 双击运行就把jar加密混淆了 并且搭配Maven插件可以完全不影响原有的编译逻辑

[这里](https://www.52pojie.cn/thread-1354106-1-1.html) 有我用的配置 xml 和 maven 插件 搭配 我自己写的 [发布工具](https://github.com/lqs1848/PublishTools) 可以实现 java SpringBoot项目 一键编译后加密混淆并发布到Linux服务器上运行



基于当前最新的 7.6版本

使用方法

​	导入项目

​	添加 lib里面 jar 的引用 和 allatori.jar 的引用

​	在 Main.java 右键运行即可

​	根目录下会生成 allatori_crack.jar 就是破解后的jar包

​	把需要破解的 allatori.jar 放在根目录即可

​	只保证 7.6 可使用



寻找 Allatori 破解的时候看到

吾爱破解上的帖子 https://www.52pojie.cn/thread-622023-1-1.html 

里面没有具体代码 没有具体实现 QAQ 只有一句 `m.insertAfter(" if        ($_!=null&&!$_.isEmpty()&&$_.equals(\"ALLATORIxDEMO\")) {\n" +"$_ = \"qtfreet00\";\n" +"}");`

还要求在ubuntu下反编译 allatori

但至少看到帖子后觉得 这不是很简单嘛 于是有了这个破解



实际操作了一下,要求在ubuntu环境下主要是因为 windows 目录不区分大小写 **Allatori** 都是 iIIii 大小写的i混合的文件名

如果只是为了大小写 那用zip操作即可



具体流程为:

zip 读取**allatori**.jar 找到所有的class

使用 javassist 读取class的字节码

判断这个类是否有 THIS_IS_DEMO_VERSION_NOT_FOR_COMMERCIAL_USE 方法

替换有String参数 String返回值的方法 返回值带有 ALLATORIxDEMO 全部替换掉即可

```
    ################################################
    #                                              #
    #        ## #   #    ## ### ### ##  ###        #
    #       # # #   #   # #  #  # # # #  #         #
    #       ### #   #   ###  #  # # ##   #         #
    #       # # ### ### # #  #  ### # # ###        #
    #                                              #
    # Obfuscation by Allatori Obfuscator v7.6 DEMO #
    #                                              #
    #           http://www.allatori.com            #
    #                                              #
    ################################################
```

版权声明的  判断 无入参 有 String 返回值 的方法逐个替换即可





全部都操作完

简单项目ok

复杂项目加密出错...



查看了一下加密信息

很多的类出现了方法名称重复

大概就是比较长的类

原来是有两总不同的加密方式 

方法较多的类会生成

​	一个 ALLATORIXDEMO 方法

​	还有一个随机英文大小写字符 的方法

```
  m.insertAfter(" if             ($_!=null&&!$_.isEmpty()&&$_.equals(\"ALLATORIxDEMO\")) {\n" +"$_ = \"qtfreet00\";\n" +"}");
```

按 qtfreet00 的做法 过滤返回参数是 ALLATORIxDEMO 

应该就是可行的 但是不知道为什么 [一个随机英文大小写支付的方法] 一样被替换了

明明有判断 $_.equals(\"ALLATORIxDEMO\") 的



打印方法入参进行对比

第一行是参数 

第二行是返回值

依次交替

> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> c.m.common.utils.i&<init>&(Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> <init>
> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> c.m.common.utils.i&ALLATORIxDEMO&Lc/m/common/utils/MapCache;
> null
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> null
> c.m.common.utils.MapCache&keys&()Ljava/util/Set;
> keys
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache&clear&()V
> clear
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;Lc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&reSize&()V
> reSize
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;JLc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Lc/m/common/utils/i;)Z
> null
> c.m.common.utils.MapCache&remove&(Ljava/lang/Object;)V
> remove
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache&contains&(Ljava/lang/String;)Z
> contains
> c.m.common.utils.MapCache&size&()I
> size
> c.m.common.utils.MapCache&<init>&()V
> <init>
> c.m.common.utils.MapCache&ALLATORIxDEMO&Lc/m/common/utils/MapEx;
> null
> c.m.common.utils.i&ALLATORIxDEMO&Lc/m/common/utils/MapCache;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.i&<init>&(Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> <init>
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&Lc/m/common/utils/MapEx;
> ALLATORIxDEMO
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&keys&()Ljava/util/Set;
> keys
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&clear&()V
> clear
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;Lc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&reSize&()V
> reSize
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;JLc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Lc/m/common/utils/i;)Z
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&remove&(Ljava/lang/Object;)V
> remove
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&contains&(Ljava/lang/String;)Z
> contains
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&size&()I
> size
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&<init>&()V
> <init>
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.i&ALLATORIxDEMO&Lc/m/common/utils/MapCache;
> ALLATORIxDEMO
> Lc/m/common/utils/MapCache;
> null
> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> null
> Lc/m/common/utils/MapCache;
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.i&<init>&(Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> <init>
> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&reSize&()V
> reSize
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Lc/m/common/utils/i;)Z
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&Lc/m/common/utils/MapEx;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;JLc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> (Lc/m/common/utils/MapCache;Ljava/lang/Object;J)V
> null
> Lc/m/common/utils/MapCache;
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c/m/common/utils/MapCache
> null
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> [INFO] Obfuscation completed. Writing log file...
> c.m.common.utils.i&ALLATORIxDEMO&Lc/m/common/utils/MapCache;
> ALLATORIxDEMO
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Lc/m/common/utils/i;)Z
> ALLATORIxDEMO
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Lc/m/common/utils/i;)Z
> " s="118" e="118
> c.m.common.utils.MapCache&ALLATORIxDEMO&(Ljava/lang/String;)Ljava/lang/String;
> ALLATORIxDEMO
> c.m.common.utils.MapCache&AllatoriDecryptString&(Ljava/lang/String;)Ljava/lang/String;
> F
> c.m.common.utils.MapCache&clear&()V
> clear
> c.m.common.utils.MapCache&clear&()V
> " s="103" e="107
> c.m.common.utils.MapCache&contains&(Ljava/lang/String;)Z
> contains
> c.m.common.utils.MapCache&contains&(Ljava/lang/String;)Z
> " s="6" e="6
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;)Ljava/lang/Object;
> " s="22" e="233
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;JLc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;JLc/m/common/utils/Callable;)Ljava/lang/Object;
> " s="47" e="224
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;Lc/m/common/utils/Callable;)Ljava/lang/Object;
> get
> c.m.common.utils.MapCache&get&(Ljava/lang/Object;Lc/m/common/utils/Callable;)Ljava/lang/Object;
> " s="115" e="115
> c.m.common.utils.MapCache&keys&()Ljava/util/Set;
> keys
> c.m.common.utils.MapCache&keys&()Ljava/util/Set;
> " s="72" e="72
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
> " s="3" e="3
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
> put
> c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
> " s="112" e="210
> c.m.common.utils.MapCache&reSize&()V
> reSize
> c.m.common.utils.MapCache&reSize&()V
> " s="40" e="190
> c.m.common.utils.MapCache&remove&(Ljava/lang/Object;)V
> remove
> c.m.common.utils.MapCache&remove&(Ljava/lang/Object;)V
> " s="71" e="209
> c.m.common.utils.MapCache&size&()I
> size
> c.m.common.utils.MapCache&size&()I
> " s="132" e="230
> c.m.common.utils.MapCache&ALLATORIxDEMO&Lc/m/common/utils/MapEx;
> ALLATORIxDEMO



对比发现 入参带有 AllatoriDecryptString 的是返回 一个随机英文大小写字符



修改判断代码 $_ 前面加上 $1.indexOf(\"AllatoriDecryptString\")

搞定收工
