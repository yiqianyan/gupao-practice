package spring.beans.support;

import spring.beans.config.MyBeanDefinition;
import spring.context.support.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//MyApplicationContext是最底层的容器实现，MyDefaultListableBeanFactory默认的容器实现，BeanFactory是顶层的容器规范
public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext{
    //存储注册信息（包括接口）    key为全类名，注意接口的MyBeanDefinition对应其某个实现类（至于是哪个，则随机，因为key相同，所以会出现值覆盖）
    protected final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
}
