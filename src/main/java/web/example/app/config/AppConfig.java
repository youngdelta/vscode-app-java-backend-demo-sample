package web.example.app.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import web.example.app.config.components.PageRequestResolver;
import web.example.app.config.interceptor.LoggerInterceptor;

@Log4j2
@Configuration
// @RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

	// @Autowired // DI 적용 
	// private PagingInterceptor pagingInterceptor;

	private final PageRequestResolver pageRequestResolver;

	public AppConfig(PageRequestResolver pageRequestResolver) {
        this.pageRequestResolver = pageRequestResolver;
    }

	
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageRequestResolver);
    }


	@Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoggerInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**")

				;
		
		registry.addInterceptor(new PagingInterceptor())
				// .addPathPatterns("/countrys-view")
				// .addPathPatterns("/**")
				.excludePathPatterns("/css/**", "/images/**", "/js/**", "/**/.json", "/error")
				;
    }

	
	
//	@Bean
	// public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
	//     SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	//     factoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
	    
	//     PageInterceptor pi = new PageInterceptor();
	//     Properties props = new Properties();
	//     props.put("helperDialect" , "mysql");
	//     props.put("reasonable" , "true"); // 문자열
	//     pi.setProperties(props);
	//     factoryBean.setPlugins(pi);
	//     return factoryBean.getObject();
	// }


	@Bean
    public FilterRegistrationBean<CookieFilter> cookieFilter() {

        log.info("### cookieFilter START");

        FilterRegistrationBean<CookieFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CookieFilter());
        registrationBean.addUrlPatterns("/*"); // 필터가 적용될 URL 패턴
		
        log.info("### cookieFilter END");
        
        return registrationBean;
    }

}
