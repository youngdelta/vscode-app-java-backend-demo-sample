package web.example.app.config.components;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import web.example.app.domain.dto.PageRequest;

@Component
public class PageRequestResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        int pageNum = Optional.ofNullable(webRequest.getParameter("pageNum"))
                              .map(Integer::parseInt)
                              .orElse(1);
        int pageSize = Optional.ofNullable(webRequest.getParameter("pageSize"))
                               .map(Integer::parseInt)
                               .orElse(10);
        return new PageRequest(pageNum, pageSize);
    }
}