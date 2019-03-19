package factory.abst;

/**
 * @author zhangjun
 * @date 2019/3/18 13:30
 */
public class HuaweiFactory implements ElectronicProductFactory {

    public MobilePhone getMobilePhone() {
        return new HuaweiMobilePhone();
    }

    public Notebook getNotebook() {
        return new HuaweiNotebook();
    }
}
