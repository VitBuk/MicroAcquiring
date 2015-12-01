package lv.lpb.services;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Target(value = {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface ServiceQualifier{
    
    public ServiceQualifier.ServiceType serviceType() default ServiceQualifier.ServiceType.ADMIN;
    
    public static enum ServiceType {
        ADMIN,
        TRAN;
    }
}
