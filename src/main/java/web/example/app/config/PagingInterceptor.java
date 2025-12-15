package web.example.app.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import web.example.app.config.global.GlobalVariables;

@Log4j2
@Component
public class PagingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("###   preHandle START");

        log.info("handler: {}", handler);

        log.info("#requestURI: {}", request.getRequestURI());

        request.getParameterNames().asIterator().forEachRemaining(param -> log.info("### -------------- param: {} @value: {}", param, request.getParameter(param)));

        // request.getAttributeNames().asIterator().forEachRemaining(param -> log.info("### Attributes: {} @@value: {}", param , request.getAttribute(param)));

                // page size
        if (request instanceof CustomRequestWrapper) {
            CustomRequestWrapper customRequest = (CustomRequestWrapper) request;
            // customRequest.addParameter(...) 등 사용 가능
            if(request.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == null || request.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == "" || request.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == "0") {
            // if(httpRequest.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == "0") {
                customRequest.addParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY, String.valueOf(GlobalVariables.DEFAULT_PAGE_SIZE));
            }
        }
    
        // request.getHeaderNames().asIterator().forEachRemaining(param -> log.info("Headers: {}", param));
        

        String pageNum = request.getParameter(GlobalVariables.DEFAULT_PAGE_NUM_KEY);
        if (pageNum == null || pageNum.isEmpty()) {
            pageNum = String.valueOf(GlobalVariables.DEFAULT_PAGE_NUM); //1
        }
        
        String pageSize = request.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY);
        if (pageSize == null || pageSize.isEmpty()) {
            pageSize = String.valueOf(GlobalVariables.DEFAULT_PAGE_SIZE); //10
        }

        // request.setAttribute("pageSize", pageSize);

        // response.setHeader("pageSize", pageSize);

        log.info("pageNum: {}", pageNum);
        log.info("pageSize: {}", pageSize);


        


        // PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        
        // request.setAttribute("pageNum", pageNum);
        // request.setAttribute("pageSize", pageSize);


        log.info("###   preHandle END");
        
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("###   postHandle START");

        request.getParameterNames().asIterator().forEachRemaining(param -> log.info("#param: {} @value: {}", param, request.getParameter(param)));



        // Page<?> datas = (Page<?>) modelAndView.getModel().get("datas");
        // String path = request.getContextPath() + request.getServletPath() + "?page";
        // PageNavigationForPageHelper pageInfo = new PageNavigationForPageHelper(datas, path);
        // modelAndView.addObject("pageInfo", pageInfo);

        log.info("###   postHandle END");
    }
}

