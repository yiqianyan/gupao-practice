package adapter;

/**
 * @author zhangjun
 * @date 2019/3/26 17:43
 */
public class ChineseSocket implements ChineseSocketInterface{
    @Override
    public void chargeByChineseInterface() {
        System.out.println("使用中国标准的插座接口充电");
    }
}
