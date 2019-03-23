package proxy.myjdkproxy;

import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2019/3/23 10:28
 */
//MyInvocationHandler实现类，有点特殊：这个类既作为获得代理对象的工厂，又要把自身注入到代理类为了调用invoke方法
public class MyJDKProxyFactory implements MyInvocationHandler{
    private Object target;
    //原材料：目标对象；产品：代理对象
    public Object getInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        return MyProxy.newProxyInstance(new MyClassLoader(),clazz.getInterfaces(),this);
    }
    //代理方法在此处就做了定义
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("向观众问好");
        Object obj = method.invoke(this.target,args);
        System.out.println("谢谢大家");
        return obj;
    }
}
