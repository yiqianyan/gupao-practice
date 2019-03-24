package strategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhangjun
 * @date 2019/3/24 16:32
 */

/**
 * 使用委派模式和策略模式模拟springMVC中DispatchServlet
 * 从委派模式的角度来看，DispatchServlet相当于manager的角色
 */
public class DispatchServlet extends HttpServlet {// extends HttpServlet

    //存储映射Controller类的信息
    class Handler {
        private Object controller;
        private Method method;
        private String url;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return this;
        }
    }

    //内部类Handler可在此处使用
    private List<Handler> handlerMapping = new ArrayList<Handler>();//策略常量

    //重写的Servlet初始化方法
    public void init() throws ServletException {
        //通常会加载项目中的所有Controller对象，此处只举例一个
        //此处涉及策略模式，一个Controller就是一个策略，每个策略放到委派模式manager统一的内存Map中（不涉及策略接口，属于策略模式另一种方式）
        try {
            Class<?> clazz = PubStudentController.class;
            handlerMapping.add(new Handler()
                    .setController(clazz.newInstance())
                    .setMethod(clazz.getMethod("getStudentById", String.class))
                    .setUrl("/pub/getStudentById"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重写的service方法
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //委派模式体现在此处。任意url都访问这个类的这个方法，然后映射到各个不同的controller
        //可以用if-else或switch来转发请求，但是此处使用策略模式（策略常量），避免了大量的逻辑判断
        //委派模式产生大量逻辑判断，策略模式避免了这些逻辑判断。简单的说：委派模式产生if-else，策略模式合并if-else
        String uri = request.getRequestURI();
        Handler handle = null;
        for (Handler h : handlerMapping) {
            if (uri.equals(h.getUrl())) {
                handle = h;
                break;
            }
        }

        Object object = null;
        try {
            object = handle.getMethod().invoke(handle.getController(), request.getParameter("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
