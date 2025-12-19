package web.example.app.config.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;

/**
 * PageHelper 자동 적용 AOP
 * @PageableQuery 어노테이션이 붙은 메서드 실행 전에 자동으로 PageHelper.startPage()를 호출
 */
@Aspect
@Component
@Log4j2
@Order(1)  // 트랜잭션보다 먼저 실행되어야 함
public class PageHelperAspect {


    /**
     * 
     */
    @Around("@annotation(com.example.demo.annotation.PageableQuery)")
    public Object handlePaging(ProceedingJoinPoint joinPoint) throws Throwable {
        
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PageableQuery pageableQuery = method.getAnnotation(PageableQuery.class);
        
        // 메서드 파라미터에서 Pageable 구현체 찾기
        Object[] args = joinPoint.getArgs();
        Pageable pageableParam = findPageableParam(args);
        
        if (pageableParam != null) {
            // 페이징 파라미터 검증 및 설정
            Integer pageNum = validateAndGetPageNum(
                pageableParam.defaultPageNum(), 
                pageableQuery.defaultPageNum()
            );
            
            Integer pageSize = validateAndGetPageSize(
                pageableParam.defaultPageSize(), 
                pageableQuery.defaultPageSize(),
                pageableQuery.maxPageSize()
            );
            
            // PageHelper 시작
            PageHelper.startPage(pageNum, pageSize);
            
            log.debug("PageHelper AOP 적용 - Method: {}, PageNum: {}, PageSize: {}", 
                     method.getName(), pageNum, pageSize);
        } else {
            // Pageable 파라미터가 없으면 기본값 사용
            PageHelper.startPage(
                pageableQuery.defaultPageNum(), 
                pageableQuery.defaultPageSize()
            );
            
            log.debug("PageHelper AOP 적용 (기본값) - Method: {}, PageNum: {}, PageSize: {}", 
                     method.getName(), 
                     pageableQuery.defaultPageNum(), 
                     pageableQuery.defaultPageSize());
        }
        
        try {
            // 실제 메서드 실행
            return joinPoint.proceed();
        } finally {
            // PageHelper ThreadLocal 정리 (선택사항, PageHelper가 자동으로 정리함)
            PageHelper.clearPage();
        }
    }

    @Around("@annotation(pageable)")
    public Object doPage(ProceedingJoinPoint joinPoint, Pageable pageable) throws Throwable {
        Object[] args = joinPoint.getArgs();
        int pageNum = pageable.defaultPageNum();
        int pageSize = pageable.defaultPageSize();

        log.info("### PageHelperAspect START");
        log.info("### PageHelperAspect pageNum : {} , pageSize : {}",pageNum, pageSize);

        // 메서드 인자에서 pageNum, pageSize 찾기 (필요시 ArgumentResolver와 연동 가능)
        for (Object arg : args) {
            if (arg instanceof Map<?, ?> map) {
                // pageNum = map.getOrDefault("pageNum", pageNum);
                // pageSize = map.getOrDefault("pageSize", pageSize);

                Object pageNumVal = map.get("pageNum");
                if (pageNumVal instanceof Integer) {
                    pageNum = (Integer) pageNumVal;
                }
                Object pageSizeVal = map.get("pageSize");
                if (pageSizeVal instanceof Integer) {
                    pageSize = (Integer) pageSizeVal;
                }
            }
        }

        // PageHelper 시작
        PageHelper.startPage(pageNum, pageSize);
        Object result = joinPoint.proceed();
        return new PageInfo<>((List<?>) result);
    }


    /**
     * 메서드 파라미터에서 Pageable 구현체 찾기
     */
    private Pageable findPageableParam(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        
        for (Object arg : args) {
            if (arg instanceof Pageable) {
                return (Pageable) arg;
            }
        }
        return null;
    }
    
    /**
     * 페이지 번호 검증
     */
    private Integer validateAndGetPageNum(Integer pageNum, int defaultPageNum) {
        if (pageNum == null || pageNum < 1) {
            return defaultPageNum;
        }
        return pageNum;
    }
    
    /**
     * 페이지 크기 검증
     */
    private Integer validateAndGetPageSize(Integer pageSize, int defaultPageSize, int maxPageSize) {
        if (pageSize == null || pageSize < 1) {
            return defaultPageSize;
        }
        if (pageSize > maxPageSize) {
            log.warn("요청한 pageSize({})가 최대값({})을 초과하여 최대값으로 조정됩니다.", 
                    pageSize, maxPageSize);
            return maxPageSize;
        }
        return pageSize;
    }
}
