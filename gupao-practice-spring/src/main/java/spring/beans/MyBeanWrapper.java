package spring.beans;

public class MyBeanWrapper {//BeanDefinition的进化版，存储对象和Class信息
    private Object wrappedInstance;//对于aop讨论，这个可能是代理对象
    //对于单例，可以直接返回wrappedInstance，但是多例情况下需要根据class来new
    private Class<?> wrappedClass;

    public MyBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public void setWrappedInstance(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return this.wrappedInstance.getClass();
    }

    public void setWrappedClass(Class<?> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }
}
