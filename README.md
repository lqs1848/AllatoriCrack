# AllatoriCrack




#### 基于当前最新的 7.6 版本

**简介:**

​	破解 Java 混淆工具 Allatori [官网](http://www.allatori.com/)	并进行了部分加密功能的小修改



allatori  本身使用方法特别简单

命令行输入

> java -Xms128m -Xmx512m -jar allatori.jar config.xml

config.xml 就是本次加密的配置文件

```xml
<config>
    <input>
        <jar in="test.jar" out="obf-test.jar"/>   <-- 需要加密的jar包 -- >
        <jar in="test2.jar" out="obf-test2.jar"/>
    </input>
    <keep-names>					<-- 哪些东西不加密  这个示例是类名和 public 方法还有变量不加密 -- >
        <class access="protected+">
            <field access="protected+"/>
            <method access="protected+"/>
        </class>
    </keep-names>
    
        <-- 需要忽略的Class -- >
    <ignore-classes>
		<class template="class *springframework*" />
		<class template="class *shardingjdbc*" />
		<class template="class *jni*" />
		<class template="class *alibaba*"/>
		<class template="class *persistence*"/>
	</ignore-classes>
        
    <property name="log-file" value="log.xml"/>	<-- 加密后输出日志文件 -- >
</config>
```

调用即可加密 test.jar 

官方的DEMO 里有更多的更详细的说明 而且是 .bat 的 双击运行就把 jar 加密混淆了 

并且搭配Maven插件可以完全不影响原有的编译逻辑

官方DEMO **step12-maven** 文件夹中就有搭配Maven的例子 搭配 我写的 [发布工具](https://github.com/lqs1848/PublishTools) 可以实现 java SpringBoot项目 一键编译后加密混淆并发布到Linux服务器上运行

还能自动加水印 官方DEMO里也有栗子





**破解方法:**

1. 导入项目

2. 添加 lib里面 jar 的引用 和 根目录中待破解的 allatori.jar 的引用
3. 在 Main.java 右键运行即可

4. 根目录下会生成 allatori_crack.jar 就是破解后的jar包

*只保证 7.6 可使用 懒得破解也可以直接用我生成的 allatori_crack.jar 就在根目录*



demo目录下有示例 并附有7.6原版

  	运行 cracked.bat 就会使用 破解版的加密 test.jar 和 test2.jar

  	运行 unmodified.bat 会用官方 原版加密 test.jar 和 test2.jar

  	加密后的文件为 obf-test.jar 和 obf-test2.jar



**破解内容:**

1. 破解前 Allatori 生成的 加密 jar 在运行时会输出

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

破解后可自定义输出内容



2. 加密后的方法名称或变量名称为 ALLATORIxDEMO 替换为 随机字母

 



**修改内容:**

1. serialVersionUID 原版Allatori 会把 serialVersionUID 给加密掉 现在默认不加密 (原版可以用xml配置标示不加密这个)

2. 修复 Allatori 原版的 BUG 加密某些类 有时会出现多个方法加密成同一个名称的问题 (方法名称不同 但是参数相同 被加密成同一个方法名 ALLATORIxDEMO 导致加密后的jar无法使用)

3. 原版加密class时会把所有的Class名字修改成随机大小写的单个字母 不同包路径下类的名称会重复 在Spring下 这样加密就会出错

   ```xml
   <keep-names>
       <class access="protected+">
           <field access="protected+"/>
           <method access="protected+"/>
       </class>
   </keep-names>
   ```

   原版需要 忽略类名加密才能兼容 Spring

   修改后 类名会加密成不重复的随机字母 兼容 不允许 Bean 重复的框架



**更新内容:**

​		2021/02/05 :  **添加功能**  修改内容中的第3点

​		2021/02/04 :  **Bug Repair** 修复继承的变量被加密成与父变量名称不相同的问题



**Ps:**

​		这玩意真不好破解 全部都是类似 IiIiii.class 的文件看的脑壳疼 w(ﾟДﾟ)w

​		相比之下 Charles 真的是太好破解了 业界良心  ヾ(≧∇≦*)ゝ





[^会随时修复Bug 可以偶尔看一下是否有修改]: 

