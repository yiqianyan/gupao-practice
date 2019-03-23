package proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhangjun
 * @date 2019/3/23 11:26
 */
public class CglibProxyFactory implements MethodInterceptor {
    public Object getInstance(Class<?> clazz) throws Exception {
        //相当于Proxy，代理的工具类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("向观众问好");
        Object obj = methodProxy.invokeSuper(o, objects);
        System.out.println("谢谢大家");
        return obj;
    }
}