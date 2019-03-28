package observer.common;

/**
 * @author zhangjun
 * @date 2019/3/28 10:35
 */
public interface Subject {
    //增加订阅者
    void attach(Observer observer);

    //删除订阅者
    void detach(Observer observer);

    //通知订阅者更新消息
    void notify(String message);
}
