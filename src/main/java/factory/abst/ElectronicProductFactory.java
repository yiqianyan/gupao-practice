package factory.abst;

/**
 * @author zhangjun
 * @date 2019/3/18 13:19
 */
//不AbstractFactory，表示一个工厂。必须保证两个方法这个工厂都能使用，比如联想都能有CPU和memory
public interface ElectronicProductFactory {

    MobilePhone getMobilePhone();

    Notebook getNotebook();
}
