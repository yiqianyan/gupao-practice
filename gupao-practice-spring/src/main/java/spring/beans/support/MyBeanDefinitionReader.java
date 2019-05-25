package spring.beans.support;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import spring.annotation.MyController;
import spring.annotation.MyService;
import spring.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//scanPackage-->classNameList-->loadBeanDefinitions
public class MyBeanDefinitionReader {
    //clazz.getName()的值
    private List<String> classNameList = new ArrayList<String>();
    //spring配置文件映射到Properties对象
    private Properties properties = new Properties();
    //常量化
    private final String SCAN_PACKAGE = "scanPackage";

    //构造参数，如：classpath:application.properties
    public MyBeanDefinitionReader(String configLoaction) {
        configLoaction = configLoaction.replace("classpath:", "");
        InputStream inputStream = null;
        try {
            Resource resource = new ClassPathResource(configLoaction);
            inputStream = resource.getInputStream();
            properties.load(inputStream);

            doScanner(properties.getProperty(SCAN_PACKAGE));
        } catch (Exception e) {
            e.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //scanPackage为包名，比如com.gupaoedu.demo
    private void doScanner(String scanPackage) throws ClassNotFoundException {
        //com.gupaoedu.demo的绝对路径
        String classRoot = this.getClass().getResource("/").getPath();
        File dir = new File(classRoot + scanPackage.replace(".", "/"));
        if (dir.exists()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File file = fileArr[i];
                if (file.isDirectory()) {
                    //递归思想
                    doScanner(scanPackage + "." + file.getName());
                } else {
                    String fullClassName = scanPackage + "." + file.getName().replace(".class", "");
                    Class clazz = Class.forName(fullClassName);
                    if (clazz.isInterface() || !(clazz.isAnnotationPresent(MyController.class) || clazz.isAnnotationPresent(MyService.class))) {
                        continue;
                    }
                    classNameList.add(clazz.getName());//spring bean全类名
                }
            }
        }
    }

    //接口也对应也有BeanDefinition，其factoryBeanName虽然对应自己，但是其beanClassName是某个实现类的
    public List<MyBeanDefinition> loadBeanDefinitions() throws ClassNotFoundException {
        List<MyBeanDefinition> list = new ArrayList<>();
        for (String className :
                classNameList) {
            Class clazz = Class.forName(className);
            if (clazz.isInterface()) {
                continue;//接口可能有@Component标识，但是不能实例化作为spring bean
            }
            list.add(doCreateBeanDefinition(toLowerFirstCase(clazz.getSimpleName()), clazz.getName()));

            //如果讨论的类有实现接口，则备份每个接口指向该类的信息。通俗地讲，@Autowired 属性类型可能为接口，但是获得bean为对应的接口实现类实例
            Class[] classArr = clazz.getInterfaces();
            for (Class c :
                    classArr) {
                list.add(doCreateBeanDefinition(toLowerFirstCase(c.getSimpleName()), clazz.getName()));
            }
        }
        return list;
    }

    //factoryBeanName:bean实例名称，比如stundentController
    //beanClassName:全类名
    private MyBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        MyBeanDefinition beanDefinition = new MyBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public Properties getProperties() {
        return properties;
    }
}
