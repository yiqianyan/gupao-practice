package proxy.dbroute;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * @author zhangjun
 * @date 2019/3/23 23:19
 */
//模拟controller
public class Test {
    public static void main(String[] args) throws Exception {
        IOrderService service = (IOrderService) new DbrouteProxyFactory().getInstance(new OrderServiceImpl());
        service.selectById("1");
    }
}
