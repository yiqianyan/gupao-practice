package factory.method;

/**
 * @author zhangjun
 * @date 2019/3/17 20:41
 */
public class HuaweiMobilePhone implements MobilePhone {
    public void call() {
        System.out.println("用华为手机打电话");
    }
}
