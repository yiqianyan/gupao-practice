package v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GpMapperProxy implements InvocationHandler{
    private GpSqlSession sqlSession;
    public GpMapperProxy(GpSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {//invoke对接口中的每个方法都处理
        Class  clazz= method.getDeclaringClass();
        return sqlSession.selectOne(clazz.getName()+"."+method.getName(),args[0]);
    }
}
