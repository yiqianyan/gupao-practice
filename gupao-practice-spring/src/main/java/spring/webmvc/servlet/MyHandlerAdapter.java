package spring.webmvc.servlet;

import spring.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyHandlerAdapter {
    //一个HandlerMapping对应一个HandlerAdapter，所以调用handle方法的时候一般先执行supports方法，避免抛异常
    public boolean supports(MyHandlerMapping handler) {
        return handler instanceof MyHandlerMapping;
    }

    //适配器：匹配request参数和controller参数，相当于对controller method 的二次封装处理
    // 默认controller参数复杂类型只有HttpServletRequest和HttpServletResponse，且默认其他参数类型都有@RequestParam
    public MyModelAndView handle(HttpServletRequest req, HttpServletResponse resp, MyHandlerMapping handler) throws Exception {
        Method method = handler.getMethod();

        //一个controller方法参数对应一个顺序，key对应@RequestParam的value值或者参数类型全类名，value为参数列表从左到右顺序值（从0开始）
        Map<String, Integer> paramIndexMap = new HashMap<>();
        //paramIndexMap之非HttpServletRequest和HttpServletResponse处理
        Annotation[][] annotations = method.getParameterAnnotations();
        //annotations.length是所有参数个数，包括HttpServletRequest和HttpServletResponse
        for (int i = 0; i < annotations.length; i++) {
            //假设每个方法参数只有一个注解@RequestParam
            Annotation[] arr = annotations[i];
            if (arr != null && arr.length > 0) {
                Annotation annotation = arr[0];
                if (annotation instanceof MyRequestParam) {
                    MyRequestParam requestParam = (MyRequestParam) annotation;
                    paramIndexMap.put(requestParam.value(), i);
                }
            }
        }
        //paramIndexMap之HttpServletRequest和HttpServletResponse处理
        Class[] paramClasses = method.getParameterTypes();
        for (int i = 0; i < paramClasses.length; i++) {
            Class paramClass = paramClasses[i];
            if (paramClass == HttpServletRequest.class || paramClass == HttpServletResponse.class) {
                paramIndexMap.put(paramClass.getName(), i);
            }
        }

        Map<String, String[]> reqMap = req.getParameterMap();
        Object[] realMethodParams = new Object[paramIndexMap.size()];
        Set<Map.Entry<String, Integer>> set = paramIndexMap.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            Class anyParamClass = paramClasses[entry.getValue()];
            if (!paramIndexMap.containsKey(entry.getKey()) || anyParamClass == HttpServletResponse.class || anyParamClass == HttpServletRequest.class) {
                continue;
            }
            //reqMap-->paramIndexMap(中介)-->paramClasses-->realMethodParams（用于method.invoke参数）
            String[] arr = reqMap.get(entry.getKey());
            realMethodParams[entry.getValue()] = convertRequestParamType(arr[0], anyParamClass);
        }
        if (paramIndexMap.containsKey(HttpServletRequest.class.getName())) {
            realMethodParams[paramIndexMap.get(HttpServletRequest.class.getName())] = req;
        }
        if (paramIndexMap.containsKey(HttpServletResponse.class.getName())) {
            realMethodParams[paramIndexMap.get(HttpServletResponse.class.getName())] = resp;
        }
        //执行controller方法
        Object retObj = method.invoke(handler.getController(), realMethodParams);

        if (retObj == null || retObj instanceof Void) {
            return null;
        }
        boolean isModelAndView = handler.getMethod().getReturnType() == MyModelAndView.class;
        if (isModelAndView) {
            return (MyModelAndView) retObj;
        }
        return null;
    }

    private Object convertRequestParamType(String value, Class paramType) {
        if (String.class == paramType) {
            return value;
        }
        if (Integer.class == paramType) {
            return Integer.valueOf(value);
        } else if (Double.class == paramType) {
            return Double.valueOf(value);
        } else {
            if (value != null) {
                return value;
            }
            return null;
        }
    }
}
