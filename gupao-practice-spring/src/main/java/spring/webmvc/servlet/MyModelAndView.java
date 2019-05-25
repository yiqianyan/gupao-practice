package spring.webmvc.servlet;

import java.util.Map;

public class MyModelAndView {
    //视图名称，比如“test”可能映射到模板文件test.html
    private String viewName;
    //返回前端页面的数据  Map<String, ?>
    private Map<String, ?> model;

    public MyModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public MyModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
