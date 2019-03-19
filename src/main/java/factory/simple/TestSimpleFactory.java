package factory.simple;

/**
 * @author zhangjun
 * @date 2019/3/17 20:39
 */
public class TestSimpleFactory {
    public static void main(String[] args) throws Exception {
        MobilePhoneFactory factory = new MobilePhoneFactory();
        MobilePhone mobilePhone=factory.create(XiaomiMobilePhone.class);
        mobilePhone.call();
    }
}
