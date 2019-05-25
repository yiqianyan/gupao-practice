package spring.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

//url,controller,method之间的映射封装
public class MyHandlerMapping {
    private Object controller;
    private Method method;
    //使用Pattern对象来匹配不同情况下的url
    private Pattern pattern;

    public MyHandlerMapping(Pattern pattern, Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
