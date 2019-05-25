package spring.context;

import spring.annotation.MyAutowired;
import spring.annotation.MyController;
import spring.annotation.MyService;
import spring.beans.MyBeanWrapper;
import spring.beans.config.MyBeanDefinition;
import spring.beans.support.MyBeanDefinitionReader;
import spring.beans.support.MyDefaultListableBeanFactory;
import spring.core.MyBeanFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {
    private String configLoaction;//假设只有一个配置文件
    private MyBeanDefinitionReader reader;

    //对于spring，如果是单例bean，则从factoryBeanObjectCache直接取对象，否则从factoryBeanInstanceCache中通过BeanWrapper new

    //单例的IOC容器缓存
    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();//key为beanClassName，缓存单例bean
    //通用的IOC容器。全类名：BeanWrapper
    private Map<String, MyBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    //new的时候读配置文件，并且refresh
    public MyApplicationContext(String configLoaction) {//参数值，如：classpath:application.properties
        this.configLoaction = configLoaction;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new MyBeanDefinitionReader(this.configLoaction);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        registerBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，提前初始化。其他的，手动getBean才初始化
        //getBean有两个使用场景，其一为业务代码手动getBean，其二是spring初始化非懒加载单例bean
        initNotLazyIocBean();
    }

    private void initNotLazyIocBean() throws Exception {
        Set<Map.Entry<String, MyBeanDefinition>> entrySet = this.beanDefinitionMap.entrySet();
        Iterator<Map.Entry<String, MyBeanDefinition>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MyBeanDefinition> entry = iterator.next();
            MyBeanDefinition beanDefinition = entry.getValue();
            if (!beanDefinition.isLazyInit()) {
                //根据全类名初始化bean实例，并可以返回
                getBean(beanDefinition.getBeanClassName());//getBean使用BeanDefinition
            }
        }
    }

    private void registerBeanDefinition(List<MyBeanDefinition> beanDefinitions) throws Exception {
        for (MyBeanDefinition beanDefinition : beanDefinitions) {
            //beanDefinitionMap的key为全类名，而不是factoryBeanName
            this.beanDefinitionMap.put(beanDefinition.getBeanClassName(), beanDefinition);
        }
    }

    //getBean不仅将spring bean实例化，而且将这个bean放到真正的ioc容器中，最后从ioc容器中找到这个生成的bean返回
    @Override
    public Object getBean(String beanClassName) throws Exception {
        //1.初始化spring bean（不处理属性对象注入，即可能出现的循环注入情况）
        MyBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanClassName);
        //返回spring bean原生对象
        Object instance = instantiateBean(beanClassName, beanDefinition);
        //使用了装饰器模式：
        //1)、保留原来的OOP关系
        //2)、需要对它进行扩展，增强（为了以后AOP打基础）
        MyBeanWrapper beanWrapper = new MyBeanWrapper(instance);
        //factoryBeanObjectCache的key有factoryBeanName，但是factoryBeanInstanceCache没有
        this.factoryBeanInstanceCache.put(beanDefinition.getBeanClassName(), beanWrapper);

        //2.对所有spring bean进行依赖注入，分成两步能够避免循环注入的问题
        //循环注入：class A{ B b;}    class B{ A a;}
        populateBean(beanClassName, new MyBeanDefinition(), beanWrapper);

        return this.factoryBeanInstanceCache.get(beanDefinition.getBeanClassName()).getWrappedInstance();
    }

    //参数beanWrapper为主类，讨论它的属性的注入
    private void populateBean(String beanClassName, MyBeanDefinition beanDefinition, MyBeanWrapper beanWrapper) throws IllegalAccessException {
        Object instance = beanWrapper.getWrappedInstance();
        Class clazz = beanWrapper.getWrappedClass();

        if (!(clazz.isAnnotationPresent(MyController.class) || clazz.isAnnotationPresent(MyService.class))) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            MyAutowired autowired = field.getAnnotation(MyAutowired.class);
            String autowiredValue = autowired.value();//别名，对应factoryBeanName
            if ("".equals(autowiredValue)) {
                autowiredValue = field.getType().getName();//如果没别名，则一般全类名从beanWrapperCache取数据
            }
            field.setAccessible(true);
            if (field.isAnnotationPresent(MyAutowired.class)) {
                if (factoryBeanInstanceCache.get(autowiredValue) == null) {
                    continue;
                }
                field.set(instance, factoryBeanInstanceCache.get(autowiredValue).getWrappedInstance());
            }
        }
    }

    //此处假设只讨论单例
    private Object instantiateBean(String beanClassName, MyBeanDefinition beanDefinition) throws Exception {
        Class clazz = Class.forName(beanClassName);
        Object object = null;
        if (factoryBeanObjectCache.containsKey(beanClassName)) {
            return this.factoryBeanObjectCache.get(beanClassName);
        } else {
            object = clazz.newInstance();

            this.factoryBeanObjectCache.put(beanClassName, object);
            //factoryBeanName
            this.factoryBeanObjectCache.put(beanDefinition.getFactoryBeanName(), object);
        }
        return object;
    }


    @Override
    public Object getBean(Class clazz) throws Exception {
        return getBean(clazz.getName());
    }

    public List<Class> getBeanClasses() {
        List<Class> resultList = new ArrayList<>();
        Set<Map.Entry<String, Object>> set = factoryBeanObjectCache.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            resultList.add(entry.getValue().getClass());
        }
        return resultList;
    }

    public Properties getProperties() {
        return reader.getProperties();
    }
}
