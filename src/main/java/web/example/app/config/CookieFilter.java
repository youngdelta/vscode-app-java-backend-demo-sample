package web.example.app.config;

import java.io.IOException;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.example.app.config.global.GlobalVariables;

public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 작업이 필요하면 여기서 수행
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 커스텀 요청 래퍼 생성
        CustomRequestWrapper wrappedRequest = new CustomRequestWrapper(httpRequest);

        // 요청 파라미터 추가
        // wrappedRequest.addParameter("newParam", "newValue");
        // wrappedRequest.addParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY, String.valueOf(GlobalVariables.DEFAULT_PAGE_SIZE));

        // page size
        /* if(httpRequest.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == null || httpRequest.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == "" || httpRequest.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == "0") {
        // if(httpRequest.getParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY) == "0") {
            wrappedRequest.addParameter(GlobalVariables.DEFAULT_PAGE_SIZE_KEY, String.valueOf(GlobalVariables.DEFAULT_PAGE_SIZE));
        } */

        // 쿠키가 존재하는지 체크
        boolean cookieExists = false;
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("myCookie".equals(cookie.getName())) {
                    cookieExists = true;
                    break;
                }
            }
        }

        // 쿠키가 없을 때만 생성
        if (!cookieExists) {
            Cookie cookie = new Cookie("myCookie", "cookieValue");
            cookie.setMaxAge(60 * 60); // 1시간
            cookie.setPath("/"); // 유효 경로
            httpResponse.addCookie(cookie);
        }

        // 다음 필터 또는 서블릿 호출
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {
        // 정리 작업이 필요하면 여기서 수행
    }
}