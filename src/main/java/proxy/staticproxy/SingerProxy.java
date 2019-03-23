package proxy.staticproxy;

/**
 * @author zhangjun
 * @date 2019/3/23 13:01
 */
public class SingerProxy implements ISinger{
    private ISinger target;
    //接收目标对象，以便调用sing方法
    public SingerProxy(ISinger target){
        this.target=target;
    }
    //对目标对象的sing方法进行功能扩展
    public void sing() {
        System.out.println("向观众问好");
        target.sing();
        System.out.println("谢谢大家");
    }
}