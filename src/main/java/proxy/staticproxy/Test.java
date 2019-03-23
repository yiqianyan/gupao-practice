package proxy.staticproxy;

/**
 * @author zhangjun
 * @date 2019/3/23 13:01
 */
public class Test {
    public static void main(String[] args) {
        //目标对象
        ISinger target = new Singer();
        //代理对象
        ISinger proxy = new SingerProxy(target);
        //执行的是代理的方法
        proxy.sing();
    }
}
