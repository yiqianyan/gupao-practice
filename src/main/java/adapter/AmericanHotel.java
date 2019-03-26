package adapter;

/**
 * @author zhangjun
 * @date 2019/3/26 17:42
 */
public class AmericanHotel {
    //旅馆中提供一个美国标准插座接口
    private AmericanSocketInterface americanSocket;

    public AmericanHotel() {
    }
    public AmericanHotel(AmericanSocketInterface americanSocket) {
        this.americanSocket = americanSocket;
    }
    public void setSocket (AmericanSocketInterface americanSocket){
        this.americanSocket = americanSocket;
    }

    //旅馆中有一个充电的功能
    public void charge(){
        //使用美国标准插座接口充电
        americanSocket.chargeByAmericanInterface();
    }
}
