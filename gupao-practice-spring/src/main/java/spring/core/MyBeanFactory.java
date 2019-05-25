package spring.core;

//BeanFactory顶层设计
public interface MyBeanFactory {
    //根据beanClassName从IOC容器中获得一个实例Bean，beanClassName：clazz.getName()
    Object getBean(String beanClassName)throws Exception;

    Object getBean(Class clazz) throws Exception;
}
