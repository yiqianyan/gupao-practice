package spring.webmvc.servlet;

import java.io.File;
import java.util.Locale;

//用来解析后台的viewName，返回一个服务端模板文件File。注意到这个模板文件File尚且没有进行渲染操作
public class MyViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    //存放模板文件的目录File
    private File templateRootDir;

    public MyViewResolver(String templateRoot) {//String ，而非File
        String templateRootPath = this.getClass().getResource("/" + templateRoot).getPath();
        this.templateRootDir = new File(templateRootPath);
    }

    //视图文件：String -->file
    public MyView resolveViewName(String viewName, Locale locale) {
        if (viewName == null || "".equals(viewName)) {
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new MyView(templateFile);
    }
}

