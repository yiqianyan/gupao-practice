package factory.abst;

/**
 * @author zhangjun
 * @date 2019/3/17 20:41
 */
//XiaomiMobilePhone和MobilePhone同一个产品族，不同等级结构
public class XiaomiMobilePhone implements MobilePhone {
    public void call() {
        System.out.println("用小米手机打电话");
    }
}
