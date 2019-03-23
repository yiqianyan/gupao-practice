package proxy.myjdkproxy;

import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2019/3/22 21:52
 */
//MyInvocationHandler实现类会被注入到代理类，作为代理类的目标对象代理方法
public interface MyInvocationHandler {
    //目标方法对应的代理方法
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
