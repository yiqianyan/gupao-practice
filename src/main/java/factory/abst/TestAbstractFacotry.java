package factory.abst;

/**
 * @author zhangjun
 * @date 2019/3/18 13:22
 */
public class TestAbstractFacotry {
    public static void main(String[] args) {
        ElectronicProductFactory xiaomiFactory = new XiaomiFactory();
        MobilePhone xiaomiMobilePhone = xiaomiFactory.getMobilePhone();
        xiaomiMobilePhone.call();
        Notebook xiaomiNotebook = xiaomiFactory.getNotebook();
        xiaomiNotebook.watchMovie();

        ElectronicProductFactory huaweiFactory = new HuaweiFactory();
        MobilePhone huaweiMobilePhone = huaweiFactory.getMobilePhone();
        huaweiMobilePhone.call();
        Notebook huaweiNotebook = huaweiFactory.getNotebook();
        huaweiNotebook.watchMovie();
    }
}
