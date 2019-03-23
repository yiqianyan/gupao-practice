package proxy.myjdkproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhangjun
 * @date 2019/3/22 21:54
 */
public class MyClassLoader extends ClassLoader {//继承ClassLoader

    @Override
    //重写findClass。作用：class文件名称-->Class对象
    protected Class<?> findClass(String fileName) throws ClassNotFoundException {
        //当前类文件MyClassLoader所在目录File
        File menuFile = new File(MyClassLoader.class.getResource("").getPath());
        //全类名字符串
        String wholeClassName = MyClassLoader.class.getPackage().getName() + "." + fileName;
        if (menuFile != null) {
            File classFile = new File(menuFile, fileName + ".class");
            if (classFile.exists()) {
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try {
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) != -1) {
                        out.write(buff, 0, len);
                    }
                    //defineClass为jdk  ClassLoader中的方法，第一个参数为全类名
                    return defineClass(wholeClassName, out.toByteArray(), 0, out.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
