# Vim,Java,Ant和Junit的自学报告

---

[TOC]

## 1.Vim

### 1.Vim介绍
1. Vim（Vi IMproved）是从vi发展出来的一个文本编辑器，而且是开源和免费的。

### 2.学习过程

1. 使用Vim打开文件
```
vim filename
```
进入全屏的编辑界面，每一行开头的~表示这一行不在文件中，在屏幕左下角显示有文件名等信息。刚开始是处于**普通模式**，此时按下i键可进入**插入模式**即可开始编辑。
2. 退出vim
在普通模式下按下Esc即可进入普通模式，此时按下“：”之后输入wq即可保存并退出vim；也可以在普通模式下按下“ZZ”保存并退出。如果不想保存可以输入“:q!"，回到打开前的状态。
3. 常用命令
u：撤销操作
dd：删除行，ndd（3dd）删除光标下n行
y：复制，yyn（yy3）复制光标下n行
p：粘贴
CTRL-R: 恢复撤销

## 2. Ant
### 1.Ant介绍
Ant是一个集编译，测试，运行与一体的自动化工具。
### 2.Ant build.xml
Ant的默认构造文件是build.xml。当执行
```
ant
```
会自动运行当前目录下的build.xml

每一个构造文件需要有一个项目元素(project)和至少一个任务(target)

project有3个属性

* name： 表示项目的名字
* default：表示默认运行的target，是必须的，通过`ant`运行。
* basedir：表示基准目录

target可以有多个属性
* name：任务名是必须的，可以通过`ant tasknme`执行此任务
* depends: 表示任务的依赖关系，可通过，连接多个依赖，被依赖的任务会先被执行。

### 3.Ant 构建Hello

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project name="helloworld" basedir="." default="build">
	<!-- 源代码src路径 -->
	<property name="src.dir" value="src"/>
	<!-- 编译文件class路径 -->
	<property name="build.dir" value="bin"/>
	<!-- 设置classpath -->
	<path id="classpath">
      <pathelement path="${build.dir}"/>
   </path>

	<!-- 编译文件，初始化目录-->
	<target name="build">
		<mkdir dir="${build.dir}"/>
		<javac destdir="${build.dir}" includeantruntime="false">
			<src path="${src.dir}"/>
		</javac>
	</target>
	<!-- 清理编译文件 -->
	<target name="clean">
		<delete>
			<fileset dir="${build.dir}">
				<include name="**/*.class"/>
			</fileset>
		</delete>
	</target>
	<!-- 执行编译文件 -->
	<target name="run">
      <java fork="yes" failonerror="yes" classname="Hello">
      	<classpath refid="classpath"/>
      </java>
   </target>
</project>
```
描述
```xml
    <!-- 源代码src路径 -->
	<property name="src.dir" value="src"/>
	<!-- 编译文件class路径 -->
	<property name="build.dir" value="bin"/>
	<!-- 设置classpath -->
	<path id="classpath">
      <pathelement path="${build.dir}"/>
   </path>
```
property标签相当于声明变量。之后可以使用`${name}`来获取它的值。

path标签被用来表示一个类路径。这里指向build.dir下的类文件。

```xml
<target name="build">
		<mkdir dir="${build.dir}"/>
		<javac destdir="${build.dir}" includeantruntime="false">
			<src path="${src.dir}"/>
		</javac>
</target>
```
javac的`includeantruntime`属性不设置的话，执行时会有警告，这里可以将其设为`false`.不过在之后编译测试文件时，需要将其设为`true`,不然会编译失败。

### 4.Ant完成junit
现在我们可以自动完成Hello项目的编译和运行了。不如再让Ant帮我们完成单元测试吧。
```xml
<project name="JunitTest" default="test" basedir=".">
   <!-- 测试文件路径 -->
   <property name="testdir" location="test" />
   <!-- 源代码路径 -->
   <property name="srcdir" location="src" />
   <!-- 设置classpath -->
   <path id="classpath.test">
      <pathelement path="/lib/junit-4.10.jar" />
      <pathelement path="${testdir}" />
      <pathelement path="${srcdir}" />
   </path>
   <!-- 清理编译文件 -->
   <target name="clean">
      <delete>
         <fileset dir="${testdir}" includes="**/*.class" />
      </delete>
   </target>
   <!-- 编译源代码 -->
   <target name="compile" depends="clean">
      <javac srcdir="${srcdir}" destdir="${testdir}" includeantruntime="true">
         <classpath refid="classpath.test"/>
      </javac>
   </target>
   <!-- 进行单元测试 -->
   <target name="test" depends="compile">
      <junit>
         <classpath refid="classpath.test"/>
         <formatter type="brief" usefile="false" />
         <test name="TestHello" />
      </junit>
   </target>
</project>
```
描述
```xml
<path id="classpath.test">
      <pathelement path="/lib/junit-4.10.jar" />
      <pathelement path="${testdir}" />
      <pathelement path="${srcdir}" />
   </path>
```
首先我们需要将junit-4.10.jar复制到基目录的lib文件夹中。
因为junit需要运行测试，所以要将testdir包含进路径中。
```xml
<target name="test" depends="compile">
      <junit>
         <classpath refid="classpath.test"/>
         <formatter type="brief" usefile="false" />
         <test name="TestHello" />
      </junit>
   </target>
</project>
```
`<formatter type="brief" usefile="false" />`中type为brief表示只输出错误细节，输出报告默认会发送到一个文件中，设置usefile为false将只在控制台显示输出信息。
至此完成了基本的Ant自动化编译，测试，运行工作。

## 3.JUnit

### JUnit介绍
JUnit是一个java的单元测试框架。

### JUnit编写HelloWorld测试
单元测试是为了检测程序是否符合期望运行。其中一方面就是看输出或函数的返回值是否符合期望。因此可以使用
断言完成测试。
`void assertEquals(boolean expected, boolean actual)`
通过
`import static org.junit.Assert.assertEquals;`
引入
判断两个值是否相等。除此之外还有许多断言方法，均可参照上述方法使用。

TestHello.java
```java
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestHello {
	Hello h = new Hello();

	@Test
	public void testPrintHello() {
		assertEquals("Hello World!" ,h.printHello());
	}
}
```
预期Hello.printHello()会输出并返回一个字符串"Hello World!"，因此只需要测试其返回值是否为"Hello World!"即可。

到这里一个单元测试就编写完了。

### 运行测试
1.使用Ant自动执行junit
2.在控制台执行
```
javac Hello.java TestHello.java
java org.junit.runner.JUnitCore TestHello
```
3.编写一个TestRunner
```java
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestHello.class);
		for (Failure failure: result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
```

## 4.Java
### 介绍
java是一门完全面向对象的编程语言。java是平台独立的，要开发java程序需要JDK(Java Development Kit)。

### Hello.java

```java
public class Hello
{ 
  String msg;
  Hello() {
    msg = "Hello World!";
  };
  public String printHello() {
    System.out.println(msg);
    return msg;
  }
  public static void main(String[] args)
  {
      Hello h = new Hello();
      h.printHello();
  }
}
```
### 编译运行
编译：`javac Hello.java`
运行：`java Hello`

### java-gui完成计算器小程序

`java.awt`是抽象的组件软件包，包含用于创建用户界面和绘制图像的所有类。
`javax.swing`是基于`java.awt`的轻量级组件包，由于是用纯java写成，因此可以跨平台运行。

首先引入相关包
```java
import java.awt.*;
import javax.swing.*;
```
接下来就可以开始实现GUI了。
首先实例化一个窗体对象
```java
 //set frame name
    JFrame frame = new JFrame("Easy Calculator");
```
JFrame 实例了一个可以放大缩小的窗体。我们之后的GUI程序就是以它为基础了。可以使用
`frame.setSize(500,500);`设置窗体大小，还可以设置它的出现的位置，关闭时的操作等等。
但要将这个窗体显示出来需要设置其可见`frame.setVisible(true);`

到这里运行这个java程序就可以看到一个空白窗口了。接下来就是往这个窗口里塞按钮，文本等控件了。方法和上述的创建窗体一样。
```java
//creat buttons
    JButton addBtn = new JButton("+");
    ...
//creat textfield
    JTextField equal = new JTextField("=");
```
[更多组件信息可以在此查看][1]


  [1]: https://docs.oracle.com/javase/8/docs/api/javax/swing/JComponent.html
  
关于布局，我使用了GridLayout();这是一个布局控制器，将容器分成大小相同的多个矩形。这样做的好处就是无须手动设置每一个控件的大小，而且随着窗体大小的变化，每个控件能自适应完成变化。
```java
//set a 2*5 layout
    frame.setLayout(new GridLayout(2, 5, 5, 5));
```
这样就将窗体分成了2*5的网格，每个网格的水平，垂直间距都是5。

最后我们需要将之前创建的各种控件添加进窗体中，这样它们才能显示出来。
`frame.add(addBtn);`按需要摆放的位置，从左至右，从上至下以此添加创建的控件。

#### 添加时间监听器

我们已经将计算器小程序的GUI设计完成了，但要使用还需要一些工作。我们希望用鼠标点击“+”按钮时，计算器能知道我们希望要进行加运算，点击“ok”时进行计算。要实现这些功能就需要监听鼠标点击这一事件。
```java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
```
首先添加对应的类。
```java
/*
 *  when an oprator button be clicked, 
 *  showing the operator on [op] text
 */
private void addListenerForBtn(JButton btn) {
    btn.addActionListener(new ActionListener() {     
      public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        JButton button = (JButton)source;
        op.setText(button.getText());
      }
    });
  };
```
使用addListener()给button添加监听。重载actionPerformed()来实现我们的需求。ActionEvent表示触发的事件的类型。可以通过`e.getSource()`获得触发此事件的控件。