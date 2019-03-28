package observer.common;

/**
 * @author zhangjun
 * @date 2019/3/28 10:36
 */
public class ConcreteObserver implements Observer {//implements Observer
    //微信用户名
    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {//被观察者持有观察者对象，然后调用观察者的方法。这个就是二者的通信
        System.out.println(name + "-" + message);
    }
}