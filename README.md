# AllatoriCrack
破解 Java 混淆工具 Allatori [官网](http://www.allatori.com/)

allatori的使用方法 官方的demo里都有 而且是 .bat 的 双击运行就把jar加密混淆了 并且搭配Maven插件可以完全不影响原有的编译逻辑

[这里](https://www.52pojie.cn/thread-1354106-1-1.html) 有我用的配置 xml 和 maven 插件 搭配 我自己写的 [发布工具](https://github.com/lqs1848/PublishTools) 可以实现 java SpringBoot项目 一键编译后加密混淆并发布到Linux服务器上运行



基于当前最新的 7.6 版本

**使用方法**

1. 导入项目

2. 添加 lib里面 jar 的引用 和 根目录中待破解的 allatori.jar 的引用
3. 在 Main.java 右键运行即可

4. 根目录下会生成 allatori_crack.jar 就是破解后的jar包

*只保证 7.6 可使用*



demo目录下有示例 并附有7.6原版

  	运行 cracked.bat 就会使用 破解版的加密 test.jar 和 test2.jar

  	运行 unmodified.bat 会用官方 原版加密 test.jar 和 test2.jar

  	加密后的文件为 obf-test.jar 和 obf-test2.jar



**破解内容**:

1. 破解生成的jar会输出

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

​	破解后可自定义输出内容



2. 加密后的方法名称或变量名称为 ALLATORIxDEMO 替换为 随机字母

 



**修改内容**:

1. serialVersionUID 原版Allatori 会把 serialVersionUID 给加密掉 现在默认不加密 (原版可以用xml配置标示不加密这个)
2. 修复 Allatori 原版的 BUG 加密某些类 有时会出现多个方法加密成同一个名称的问题 (方法名称不同 但是参数相同 被加密成同一个方法名 ALLATORIxDEMO 导致加密后的jar无法使用)