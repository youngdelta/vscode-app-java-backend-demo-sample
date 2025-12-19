package web.example.app.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 정렬 기능이 포함된 고급 페이징 어노테이션
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableQueryWithSort {
    
    int defaultPageNum() default 1;
    int defaultPageSize() default 10;
    int maxPageSize() default 100;
    
    /**
     * 기본 정렬 컬럼
     */
    String defaultOrderBy() default "id";
    
    /**
     * 기본 정렬 방향 (ASC/DESC)
     */
    String defaultOrderDirection() default "ASC";
    
    /**
     * 허용된 정렬 컬럼 목록 (보안을 위해)
     */
    String[] allowedOrderColumns() default {};
}