package proxy.dbroute;

/**
 * @author zhangjun
 * @date 2019/3/23 23:29
 */
// 存储DB_2017,DB_2018之类数据
public class DynamicDataSourceEntity {
    public final static String DEFAULE_SOURCE = "DB_2017";
    //使用ThreadLocal
    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    private DynamicDataSourceEntity() {
    }

    public static String get() {
        return local.get();
    }

    public static void restore() {
        local.set(null);
    }

    public static void set(int year) {
        local.set("DB_" + year);
    }

}
