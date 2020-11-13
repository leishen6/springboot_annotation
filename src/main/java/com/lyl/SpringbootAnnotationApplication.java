package com.lyl;

import com.lyl.interceptor.ResponseResultInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author 【 木子雷 】 公众号
 *         启动器
 */
@SpringBootApplication
public class SpringbootAnnotationApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAnnotationApplication.class, args);
    }


    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(new ResponseResultInterceptor())
                // 定义拦截路径
                .addPathPatterns("/**")
                // 定义放行路径
                .excludePathPatterns("/index/**");
    }
}
