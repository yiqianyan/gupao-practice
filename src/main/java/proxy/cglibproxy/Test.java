package proxy.cglibproxy;

import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @author zhangjun
 * @date 2019/3/23 11:26
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Singer singer = (Singer) new CglibProxyFactory().getInstance(Singer.class);
        singer.sing();
    }
}