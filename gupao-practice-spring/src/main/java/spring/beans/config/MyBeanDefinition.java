package spring.beans.config;

//在spring中对应为接口而不是类
public class MyBeanDefinition {
    //beanClassName对应：class对象.getName()
    private String beanClassName;
    private boolean lazyInit = false;
    //spring bean对应的名称，比如StudentController-->stundentController
    private String factoryBeanName;
    //默认为true
    private boolean isSingleton = true;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }
}
