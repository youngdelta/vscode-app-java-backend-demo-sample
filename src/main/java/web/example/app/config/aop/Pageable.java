package web.example.app.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pageable {
    int defaultPageNum() default 1;
    int defaultPageSize() default 10;
    
    int maxPageSize() default 100;
    int defaultSort() default 1;
    
    int getPageNumber();
    int getPageSize();

    String defaultSortColumn() default "";
    String defaultSortDirection() default "asc";
    
}
