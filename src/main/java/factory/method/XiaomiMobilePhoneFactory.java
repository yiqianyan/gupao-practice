package factory.method;

/**
 * @author zhangjun
 * @date 2019/3/17 20:38
 */
public class XiaomiMobilePhoneFactory implements MobilePhoneFactory{
    public MobilePhone create() {
        return new XiaomiMobilePhone();
    }
}
