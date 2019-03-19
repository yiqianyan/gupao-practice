package factory.simple;

/**
 * @author zhangjun
 * @date 2019/3/17 20:38
 */
public class MobilePhoneFactory {
    public MobilePhone create(Class<? extends MobilePhone> clazz) throws Exception{
        if(clazz==null){
            return null;
        }
        return clazz.newInstance();
    }
}
