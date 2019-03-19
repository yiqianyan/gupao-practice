package factory.abst;

/**
 * @author zhangjun
 * @date 2019/3/18 13:30
 */
public class XiaomiFactory implements ElectronicProductFactory {

    public MobilePhone getMobilePhone() {
        return new XiaomiMobilePhone();
    }

    public Notebook getNotebook() {
        return new XiaomiNotebook();
    }
}
