package observer.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjun
 * @date 2019/3/28 10:36
 */
public class ConcreteSubject implements Subject {//implements Subject
    //储存订阅公众号的微信用户
    private List<Observer> weixinUserlist = new ArrayList<Observer>();

    @Override
    public void attach(Observer observer) {
        weixinUserlist.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        weixinUserlist.remove(observer);
    }

    @Override
    public void notify(String message) {
        for (Observer observer : weixinUserlist) {
            observer.update(message);//通过循环对所有观察者都操作，发布订阅的思想
        }
    }
}
