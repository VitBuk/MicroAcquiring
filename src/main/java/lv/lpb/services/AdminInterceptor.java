package lv.lpb.services;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminInterceptor.class);
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        log.debug(context.getContextData().entrySet().toString());
        
        // not necessarily Object, can switch it, if know return type of my EJB method
        Object result = context.proceed();
        
        return result;
    }
}
