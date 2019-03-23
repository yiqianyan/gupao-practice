package proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhangjun
 * @date 2019/3/23 11:26
 */
public class JDKProxyFactory implements InvocationHandler {
    //不ISinger，因为是动态的，而不是特定的
    private Object target;
    //参数target为目标对象，返回值为代理对象
    public Object getInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        //newProxyInstance产生代理对象，注意到第2个参数可能多接口
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }
    //重写InvocationHandler方法，第二个参数代表某一个需要代理的方法（注意不是多个方法，若有多个方法都需要代理处理，则都会分别执行这个方法）
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("向观众问好");
        Object obj = method.invoke(this.target,args);
        System.out.println("谢谢大家");
        return obj;
    }
}