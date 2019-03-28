package observer.common;

/**
 * @author zhangjun
 * @date 2019/3/28 10:36
 */
public class Test {
    public static void main(String[] args) {
        ConcreteSubject concreteSubject=new ConcreteSubject();
        //创建微信用户
        ConcreteObserver user1=new ConcreteObserver("杨影枫");
        ConcreteObserver user2=new ConcreteObserver("月眉儿");
        ConcreteObserver user3=new ConcreteObserver("紫轩");
        //订阅公众号
        concreteSubject.attach(user1);
        concreteSubject.attach(user2);
        concreteSubject.attach(user3);
        //公众号更新发出消息给订阅的微信用户
        concreteSubject.notify("刘望舒的专栏更新了");
    }
}
