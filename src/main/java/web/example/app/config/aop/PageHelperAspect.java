package web.example.app.config.aop;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class PageHelperAspect {

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
}
