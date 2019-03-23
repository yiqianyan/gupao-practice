package proxy.cglibproxy;

/**
 * @author zhangjun
 * @date 2019/3/23 11:25
 */
public class Singer {//对于cglib，此处不需要通过实现接口来绑定需要代理的方法
    public void sing() {
        System.out.println("唱一首歌");
    }
}

