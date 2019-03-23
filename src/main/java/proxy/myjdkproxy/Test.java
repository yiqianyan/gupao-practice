package proxy.myjdkproxy;

import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2019/3/23 10:46
 */
public class Test {
    public static void main(String[] args) throws Exception {
        MyJDKProxyFactory jdkProxyFactory = new MyJDKProxyFactory();
        Object proxy = jdkProxyFactory.getInstance(new Singer());
        Method method = proxy.getClass().getMethod("sing", null);
        method.invoke(proxy);
    }
}
