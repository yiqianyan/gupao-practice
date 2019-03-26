package adapter;

/**
 * @author zhangjun
 * @date 2019/3/26 17:43
 */
public class SocketAdapter  implements AmericanSocketInterface{//实现旧接口（旅馆原本有的美国接口）
    //组合新接口（中国游客需要的中国标准接口）
    private ChineseSocketInterface chineseSocket;
    //注入新接口实例
    public SocketAdapter(ChineseSocketInterface chineseSocket) {
        this.chineseSocket = chineseSocket;
    }

    //如此，AmericanSocketInterface实例的chargeByAmericanInterface方法可以调用中国标准接口
    @Override
    public void chargeByAmericanInterface() {
        chineseSocket.chargeByChineseInterface();
    }
}
