package web.example.app.config.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import web.example.app.config.components.PageableWithSort;

/**
 * 정렬 기능이 포함된 고급 PageHelper AOP
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class PageHelperWithSortAspect {
    
    @Around("@annotation(com.example.demo.annotation.PageableQueryWithSort)")
    public Object handlePagingWithSort(ProceedingJoinPoint joinPoint) throws Throwable {
        
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PageableQueryWithSort annotation = method.getAnnotation(PageableQueryWithSort.class);
        
        Object[] args = joinPoint.getArgs();
        PageableWithSort pageableParam = findPageableWithSortParam(args);
        
        Integer pageNum;
        Integer pageSize;
        String orderBy;
        String orderDirection;
        
        if (pageableParam != null) {
            pageNum = validatePageNum(pageableParam.getPageNumber(), annotation.defaultPageNum());
            pageSize = validatePageSize(
                pageableParam.getPageSize(), 
                annotation.defaultPageSize(), 
                annotation.maxPageSize()
            );
            
            // 정렬 컬럼 검증
            orderBy = validateOrderBy(
                pageableParam.getOrderBy(), 
                annotation.defaultOrderBy(),
                annotation.allowedOrderColumns()
            );
            
            orderDirection = validateOrderDirection(
                pageableParam.getOrderDirection(), 
                annotation.defaultOrderDirection()
            );
        } else {
            pageNum = annotation.defaultPageNum();
            pageSize = annotation.defaultPageSize();
            orderBy = annotation.defaultOrderBy();
            orderDirection = annotation.defaultOrderDirection();
        }
        
        // 정렬 정보와 함께 PageHelper 시작
        String orderByClause = orderBy + " " + orderDirection;
        PageHelper.startPage(pageNum, pageSize, orderByClause);
        
        log.debug("PageHelper AOP (with Sort) - Method: {}, Page: {}/{}, OrderBy: {}", 
                 method.getName(), pageNum, pageSize, orderByClause);
        
        try {
            return joinPoint.proceed();
        } finally {
            PageHelper.clearPage();
        }
    }
    
    private PageableWithSort findPageableWithSortParam(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof PageableWithSort) {
                return (PageableWithSort) arg;
            }
        }
        return null;
    }
    
    private Integer validatePageNum(Integer pageNum, int defaultValue) {
        return (pageNum == null || pageNum < 1) ? defaultValue : pageNum;
    }
    
    private Integer validatePageSize(Integer pageSize, int defaultValue, int maxValue) {
        if (pageSize == null || pageSize < 1) {
            return defaultValue;
        }
        return Math.min(pageSize, maxValue);
    }
    
    /**
     * SQL Injection 방지를 위한 정렬 컬럼 검증
     */
    private String validateOrderBy(String orderBy, String defaultValue, String[] allowedColumns) {
        if (orderBy == null || orderBy.trim().isEmpty()) {
            return defaultValue;
        }
        
        // 허용된 컬럼이 지정되어 있으면 검증
        if (allowedColumns.length > 0) {
            List<String> allowed = Arrays.asList(allowedColumns);
            if (!allowed.contains(orderBy)) {
                log.warn("허용되지 않은 정렬 컬럼: {}. 기본값({})으로 변경", orderBy, defaultValue);
                return defaultValue;
            }
        }
        
        // SQL Injection 기본 방어
        if (!orderBy.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            log.warn("유효하지 않은 정렬 컬럼 형식: {}. 기본값({})으로 변경", orderBy, defaultValue);
            return defaultValue;
        }
        
        return orderBy;
    }
    
    private String validateOrderDirection(String direction, String defaultValue) {
        if (direction == null || direction.trim().isEmpty()) {
            return defaultValue;
        }
        
        String upper = direction.toUpperCase();
        if ("ASC".equals(upper) || "DESC".equals(upper)) {
            return upper;
        }
        
        return defaultValue;
    }
}