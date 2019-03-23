package proxy.dbroute;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhangjun
 * @date 2019/3/23 23:22
 */
public class DbrouteProxyFactory implements InvocationHandler {
    private Object target;

    public Object getInstance(Object target) throws Exception {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //before
        //进行数据源的切换
        System.out.println("Proxy before method");
        int currentYear = LocalDateTime.now().getYear();
        System.out.println("静态代理类自动分配到【DB_" + currentYear + "】数据源处理数据");
        DynamicDataSourceEntity.set(currentYear);

        Object obj = method.invoke(this.target, args);

        //after
        System.out.println("Proxy after method");
        DynamicDataSourceEntity.restore();

        return obj;
    }
}
