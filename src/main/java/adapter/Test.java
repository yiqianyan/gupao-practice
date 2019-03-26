package adapter;

/**
 * @author zhangjun
 * @date 2019/3/26 17:43
 */
public class Test {
    public static void main(String[] args) {
        ChineseSocketInterface chineseSocket = new ChineseSocket();
        AmericanHotel hotel = new AmericanHotel();
        //适配器注入新接口实例
        SocketAdapter socketAdapter = new SocketAdapter(chineseSocket);
        //酒店注入适配器实例
        hotel.setSocket(socketAdapter);
        //酒店提供的充电接口适用新接口标准
        hotel.charge();
    }
}