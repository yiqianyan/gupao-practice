package spring.webmvc.servlet;

import spring.annotation.MyController;
import spring.annotation.MyRequestMapping;
import spring.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDispatcherServlet extends HttpServlet {
    //web.xml中的init param key   此处它对应的value为：classpath:application.properties
    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    //DispatcherServlet中使用到spring容器
    private MyApplicationContext context;

    private List<MyHandlerMapping> handlerMappings = new ArrayList<>();
    //MyHandlerMapping和MyHandlerAdapter的映射
    private Map<MyHandlerMapping, MyHandlerAdapter> handlerAdapters = new HashMap<>();

    private List<MyViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        MyHandlerMapping handler = getHandler(req);

        if (handler == null) {
            processDispatchResult(req, resp, new MyModelAndView("404"));
            return;
        }

        //2、获得HandlerAdapter
        MyHandlerAdapter ha = getHandlerAdapter(handler);

        //3、真正的调用controller方法,并且所有controller方法都会返回ModelAndView
        //controller method的异常会到此处，然后给doPost方法
        MyModelAndView mv = ha.handle(req, resp, handler);
        //输出模板文件给浏览器
        processDispatchResult(req, resp, mv);
    }

    private MyHandlerAdapter getHandlerAdapter(MyHandlerMapping handler) {
        MyHandlerAdapter handlerAdapter = handlerAdapters.get(handler);
        if (!handlerAdapter.supports(handler)) {
            return null;
        }
        return handlerAdapter;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, MyModelAndView modelAndView) {
        //ModleAndView变成一个HTML、OuputStream、json、freemark、veolcity等输出到浏览器
        if (null == modelAndView) {
            return;
        }
        for (MyViewResolver viewResolver : this.viewResolvers) {
            MyView view = viewResolver.resolveViewName(modelAndView.getViewName(), null);
            try {
                view.render(modelAndView.getModel(), req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;//假设只有一种模板引擎
        }
    }

    private MyHandlerMapping getHandler(HttpServletRequest req) {
        for (MyHandlerMapping handlerMapping :
                handlerMappings) {
            Pattern pattern = handlerMapping.getPattern();
            Matcher matcher = pattern.matcher(req.getRequestURI());//matcher
            if (matcher.find()) {//find
                return handlerMapping;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、初始化spring容器，所以说springMVC完全基于spring
        context = new MyApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2、初始化Spring MVC 九大组件
        initStrategies(context);
    }

    private void initStrategies(MyApplicationContext context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping，必须实现
        initHandlerMappings(context);
        //初始化参数适配器，必须实现
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器，必须实现
        initViewResolvers(context);
        //参数缓存器
        initFlashMapManager(context);
    }

    private void initFlashMapManager(MyApplicationContext context) {
    }

    private void initViewResolvers(MyApplicationContext context) {
        Properties properties = context.getProperties();
        //properties文件中：templateRoot=layouts
        String templateRoot = properties.getProperty("templateRoot");
        //一类模板（比如jsp）对应一个ViewResolver
        this.viewResolvers.add(new MyViewResolver(templateRoot));
    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {
    }

    private void initHandlerAdapters(MyApplicationContext context) {
        for (MyHandlerMapping handlerMapping :
                handlerMappings) {
            handlerAdapters.put(handlerMapping, new MyHandlerAdapter());//对HandlerAdapter直接new，不需带参数
        }
    }

    private void initHandlerMappings(MyApplicationContext context) {
        List<Class> classList = context.getBeanClasses();
        int listSize = classList.size();
        for (int i = 0; i < listSize; i++) {
            Class clazz = classList.get(i);
            if (clazz.isAnnotationPresent(MyController.class)) {
                if (!clazz.isAnnotationPresent(MyRequestMapping.class)) {
                    throw new RuntimeException("指定controller类没有MyRequestMapping注解");
                }
                MyRequestMapping myRequestMapping = (MyRequestMapping) clazz.getAnnotation(MyRequestMapping.class);
                String firstUrl = myRequestMapping.value().replace("/", "");
                String secondUrl = "";

                Method[] methods = clazz.getDeclaredMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if (method.isAnnotationPresent(MyRequestMapping.class)) {
                        MyRequestMapping myRequestMapping2 = (MyRequestMapping) method.getAnnotation(MyRequestMapping.class);
                        secondUrl = myRequestMapping2.value().replace("/", "");
                    }

                    Pattern pattern = Pattern.compile(firstUrl + "/" + secondUrl);
                    MyHandlerMapping myHandlerMapping = null;
                    try {
                        myHandlerMapping = new MyHandlerMapping(pattern, context.getBean(clazz), method);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handlerMappings.add(myHandlerMapping);
                }
            }
        }
    }

    private void initThemeResolver(MyApplicationContext context) {
    }

    private void initLocaleResolver(MyApplicationContext context) {
    }

    private void initMultipartResolver(MyApplicationContext context) {
    }
}
