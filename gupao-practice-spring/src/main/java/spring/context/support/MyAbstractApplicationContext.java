package spring.context.support;

public class MyAbstractApplicationContext {
    //protected：提供给子类重写。最少知道原则
    protected void refresh() throws Exception {}
}
