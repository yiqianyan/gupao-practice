package v1;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

public class GpConfiguration {
    public static final ResourceBundle resourceBundle;
    static {
        resourceBundle=ResourceBundle.getBundle("v1sql");
    }
    public <T> T getMapper(Class clazz, GpSqlSession sqlSession) {//GpConfiguration中提供生成代理对象的方法
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new GpMapperProxy(sqlSession));
    }
}
