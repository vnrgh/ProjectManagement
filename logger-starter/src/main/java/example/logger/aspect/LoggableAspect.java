package example.logger.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

@Aspect
public class LoggableAspect {
    @Pointcut("@annotation(example.logger.aspect.Loggable)")
    public void loggableMethod() {
    }

    @Around("loggableMethod()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Thread.sleep(50);
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println("Exception occurred");
            throw throwable;
        } finally {
            stopWatch.stop();
            System.out.println("Execution time for " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }
}
