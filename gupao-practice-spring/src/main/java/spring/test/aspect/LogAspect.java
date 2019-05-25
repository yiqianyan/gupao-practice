package spring.test.aspect;

import spring.aop.aspect.MyJoinPoint;

public class LogAspect {
    public void before(MyJoinPoint joinPoint) {
        System.out.println("====================================================前置通知");
        System.out.println(joinPoint.getThis());
        System.out.println(joinPoint.getArguments());
        System.out.println(joinPoint.getMethod());
    }

    public void after(MyJoinPoint joinPoint) {
        System.out.println("====================================================后置通知");
    }

    public void afterThrowing(MyJoinPoint joinPoint, Throwable throwable) {
        System.out.println("====================================================异常通知");
    }
}
