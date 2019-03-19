package factory.method;

/**
 * @author zhangjun
 * @date 2019/3/17 22:01
 */
public class TestMethodFacotry {
    public static void main(String[] args) {
        MobilePhoneFactory xiaomiMobilePhoneFactory = new XiaomiMobilePhoneFactory();
        MobilePhone xiaomiMobilePhone = xiaomiMobilePhoneFactory.create();
        xiaomiMobilePhone.call();

        MobilePhoneFactory huaweiMobilePhoneFactory = new HuaweiMobilePhoneFactory();
        MobilePhone huaweiMobilePhone = huaweiMobilePhoneFactory.create();
        huaweiMobilePhone.call();
    }
}
