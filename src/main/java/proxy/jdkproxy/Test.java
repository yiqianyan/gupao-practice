package proxy.jdkproxy;

import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2019/3/23 11:26
 */
public class Test {
    public static void main(String[] args) throws Exception{
        //生产代理对象的类
        JDKProxyFactory jdkProxyFactory=new JDKProxyFactory();
        //proxy为代理对象，参数为要代理的对象
        Object proxy = jdkProxyFactory.getInstance(new Singer());
        //调用代理对象封装的同名方法sing
        Method method = proxy.getClass().getMethod("sing",null);
        method.invoke(proxy);
    }
}
