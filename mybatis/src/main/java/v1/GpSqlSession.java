package v1;

public class GpSqlSession {//selectOne,getMapper
    private GpConfiguration configuration;
    private GpExecutor executor;
    public GpSqlSession(GpConfiguration configuration, GpExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }
    //注意statementId的意思，比如配置文件中一个select标签的id值
    public <T> T selectOne(String statementId, Object paramater) {
        //SqlSession中使用executor
        return executor.query(GpConfiguration.resourceBundle.getString(statementId), paramater);
    }
    //获得代理对象，通过configuration.getMapper
    public <T> T getMapper(Class clazz) {
       return configuration.getMapper(clazz,this);
    }
}