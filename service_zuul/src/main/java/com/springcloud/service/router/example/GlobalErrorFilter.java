package com.springcloud.service.router.example;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;

/**
 * post阶段不要有异常，因为一旦有异常后就会造成该过滤器后面其它post过滤器将不再被执行
 *
 * 当Zuul在执行过程中抛出一个异常时，error过滤器就会被执行（本过滤器为一个全局性的过滤器）。
 * 而SendErrorFilter只有在RequestContext.getThrowable()不为空的时候才会执行。
 * 它将错误信息设置到请求的javax.servlet.error.*属性中，并转发Spring Boot的错误页面
 * @author cctv 2018/1/28
 */
@Component
@Slf4j
public class GlobalErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        Throwable throwable = context.getThrowable();
        log.error("[ErrorFilter] error message: {}", throwable.getCause().getMessage());
        context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        context.set("error.exception", throwable.getCause());
        return null;
    }
}