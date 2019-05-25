package spring.test;

import spring.context.MyApplicationContext;

public class Test {
    public static void main(String[] args) {
//        String classRoot = this.getClass().getResource("/").getPath();
        Object object=null;
        MyApplicationContext applicationContext = new MyApplicationContext("classpath:application.properties");
        try {
              object = applicationContext.getBean("spring.test.controller.DemoController");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
