package web.example.app.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드에 이 어노테이션을 붙이면 자동으로 페이징이 적용됩니다
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableQuery {
    /**
     * 기본 페이지 번호
     */
    int defaultPageNum() default 1;
    /**
     * 기본 페이지 사이즈
     */
    int defaultPageSize() default 10;
    /**
     * 최대 페이지 사이즈
     */
    int maxPageSize() default 100;
}
