package spring.test.controller;


import spring.annotation.MyAutowired;
import spring.annotation.MyController;
import spring.annotation.MyRequestMapping;
import spring.annotation.MyRequestParam;
import spring.test.service.impl.DemoServiceImpl;
import spring.webmvc.servlet.MyModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@MyController
@MyRequestMapping("/demo")
public class DemoController {
//    private MyModelAndView out(HttpServletResponse resp,String str){
//        try {
//            resp.getWriter().write(str);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @MyAutowired
    private DemoServiceImpl demoService;

    @MyRequestMapping("/query")
    public MyModelAndView query(HttpServletRequest req, HttpServletResponse resp,
                                @MyRequestParam("name") String name) {
//
        try {
            String result = demoService.get(name);
//            resp.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("detail", e.getMessage());
//			System.out.println(Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
            model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", ""));
            return new MyModelAndView("500", model);
        }
        return null;
    }

    @MyRequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse resp,
                    @MyRequestParam("a") Integer a, @MyRequestParam("b") Integer b) {
        try {
            resp.getWriter().write(a + "+" + b + "=" + (a + b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @MyRequestMapping("/sub")
    public void sub(HttpServletRequest req, HttpServletResponse resp,
                    @MyRequestParam("a") Double a, @MyRequestParam("b") Double b) {
        try {
            resp.getWriter().write(a + "-" + b + "=" + (a - b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @MyRequestMapping("/remove")
    public String remove(@MyRequestParam("id") Integer id) {
        return "" + id;
    }

}
