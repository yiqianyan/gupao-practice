package proxy.myjdkproxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2019/3/22 21:53
 */
//本类中定义一个方法newProxyInstance用来产生代理对象
//此案例只讨论目标类只实现了一个接口，并且产生的代理类名称为$proxy0
public class MyProxy {
    private static final String ln = "\r\n";

    //第二个参数可以对应目标对象多个接口
    public static Object newProxyInstance(MyClassLoader classLoader, Class<?>[] interfaces, MyInvocationHandler h) throws Exception {
        //1.生成一个全新的代理类
        String proxyJavaFileContentStr = generateProxyJavaFileStr(interfaces);
        //2.生成代理类java文件到磁盘
        File proxyJavaFile = new File(MyProxy.class.getResource("").getPath(), "$proxy0.java");
        FileWriter fileWriter = new FileWriter(proxyJavaFile);
        fileWriter.write(proxyJavaFileContentStr);
        fileWriter.flush();
        fileWriter.close();
        //3.把刚刚生成的java文件编译成class文件，注意需要另外引入：dk lib tools.jar
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manage = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = manage.getJavaFileObjects(proxyJavaFile);//proxyJavaFile
        JavaCompiler.CompilationTask task = compiler.getTask(null, manage, null, null, null, iterable);
        task.call();
        manage.close();
        //4.编译生成的.class文件加载到JVM中来
        Class proxyClass = classLoader.findClass("$proxy0");
        proxyJavaFile.delete();//删除java文件，只需保留class文件
        //5、返回字节码重组以后的新的代理对象
        Constructor constructor = proxyClass.getConstructor(MyInvocationHandler.class);
        return constructor.newInstance(h);
    }

    //以字符串形式生成代理类中内容
    private static String generateProxyJavaFileStr(Class<?>[] interfaces) {
        Class<?> clazz = interfaces[0];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package proxy.myjdkproxy;").append(ln);
        stringBuilder.append("import proxy.myjdkproxy.").append(clazz.getSimpleName()).append(";").append(ln);
        stringBuilder.append("import java.lang.reflect.*;").append(ln);

        stringBuilder.append("public class $proxy0 implements ").append(clazz.getSimpleName()).append("{").append(ln);
        stringBuilder.append("   MyInvocationHandler h;").append(ln);
        stringBuilder.append("   public $proxy0(MyInvocationHandler h) {").append(ln);
        stringBuilder.append("      this.h = h;").append(ln);
        stringBuilder.append("   }").append(ln);

        for (Method method : clazz.getMethods()) {
            stringBuilder.append("public ").append(method.getReturnType().getName()).append(" ").append(method.getName()).append(" ( ");
            stringBuilder.append(") throws  Throwable {").append(ln);
            stringBuilder.append("Method method =").append(clazz.getSimpleName()).append(".class.getMethod(\"");
            stringBuilder.append(method.getName()).append("\"").append(");").append(ln);
            stringBuilder.append("Object retObj=null;");
            if (hasReturnValue(method.getReturnType())) {//不void返回类型
                stringBuilder.append("retObj=this.h.invoke(this,method,new Object[]{});").append(ln);
                stringBuilder.append("return retObj").append(ln);
            } else {
                stringBuilder.append("this.h.invoke(this,method,new Object[]{});").append(ln);
                stringBuilder.append("return ").append(ln);
            }
            stringBuilder.append(";").append(ln);
            stringBuilder.append("}").append(ln);
            stringBuilder.append("}").append(ln);
        }
        return stringBuilder.toString();
    }

    //字符串的首字母转成小写
    private static String toLowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    //判断返回类型是否为void，是的话return false;
    private static boolean hasReturnValue(Class<?> clazz) {
        return clazz != void.class;
    }
}
