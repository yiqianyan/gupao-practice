package observer.jdk;


import java.util.Observable;
import java.util.Observer;

/**
 * @author zhangjun
 * @date 2019/3/28 10:36
 */
//微信用户类
//implements Observer表示观察者
public class WeixinUser implements Observer {
    //微信用户名
    private String name;
    public WeixinUser(String name) {
        this.name = name;
    }
    //重写，第一个参数表示被观察者对象，第二个参数表示发布的信息
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name + "-" + arg);
    }
}