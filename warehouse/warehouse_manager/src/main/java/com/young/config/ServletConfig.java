package com.young.config;

import com.young.fillter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.Filter;

@Configuration
public class ServletConfig {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        //创建SecurityFilter对象
        SecurityFilter securityFilter = new SecurityFilter();
        //注册SecurityFilter
        filterFilterRegistrationBean.setFilter(securityFilter);
        //给SecurityFilter对象注入redis模板
        securityFilter.setStringRedisTemplate(redisTemplate);
        //配置SecurityFilter拦截所有请求
        filterFilterRegistrationBean.addUrlPatterns("/*");

        return filterFilterRegistrationBean;
    }

}
