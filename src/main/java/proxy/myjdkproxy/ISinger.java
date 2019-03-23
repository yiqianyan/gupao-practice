package proxy.myjdkproxy;

/**
 * @author zhangjun
 * @date 2019/3/23 11:25
 */
public interface ISinger {
    //不参数，也throws Throwable，使得仿写jdk更简单
    void sing() throws Throwable;
}