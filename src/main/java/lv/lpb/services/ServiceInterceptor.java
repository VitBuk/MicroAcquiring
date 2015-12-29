package lv.lpb.services;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.InvocationContext;
import lv.lpb.services.ServiceInterceptor.ProfileExecTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ProfileExecTime
@Interceptor
public class ServiceInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ServiceInterceptor.class);

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        log.trace(context.getMethod().getName());
        long startTime = System.nanoTime();

        // not necessarily Object, can switch it, if know return type of my EJB method
        Object result = context.proceed();

        long endTime = System.nanoTime();
        log.trace("Method execution time={} milis", (endTime - startTime) / 1000000);
        return result;
    }

    @Inherited
    @InterceptorBinding
    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface ProfileExecTime {

    }
}
