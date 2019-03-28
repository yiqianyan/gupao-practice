package observer.jdk;

import com.sun.org.apache.xpath.internal.operations.String;
import observer.common.Observer;
import observer.common.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author zhangjun
 * @date 2019/3/28 10:36
 */
//公众号
//extends Observable，表示这个是被观察者
public class PublicAddress extends Observable {
    //一般，被观察者都是单例的
    private PublicAddress() {
        if (LazyHolder.LAZY != null) {
            throw new RuntimeException("不允许创建多个实例");
        }
    }
    public static final PublicAddress getInstance() {
        return LazyHolder.LAZY;
    }
    private static class LazyHolder {
        private static final PublicAddress LAZY = new PublicAddress();
    }

    //单例模式类中可以有其他非static方法，供单例调用
    //这个不是重写的方法
    public void notifyAllUser() {
        //这个需要有
        setChanged();
        //只能一个参数，表示发布的信息
        notifyObservers("刘望舒的专栏更新了");
    }
}
