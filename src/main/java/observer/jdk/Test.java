package observer.jdk;

/**
 * @author zhangjun
 * @date 2019/3/28 10:36
 */
public class Test {
    public static void main(String[] args) {
        PublicAddress subject=PublicAddress.getInstance();
        //需要绑定观察者和被观察者的关系，使用Observable中的方法addObserver
        subject.addObserver(new WeixinUser("杨影枫"));
        subject.addObserver(new WeixinUser("月眉儿"));
        subject.addObserver(new WeixinUser("紫轩"));
        //被观察者的方法调用，用于发布信息
        subject.notifyAllUser();
    }
}
