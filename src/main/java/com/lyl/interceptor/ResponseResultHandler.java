package com.lyl.interceptor;

import com.lyl.annotation.ResponseResult;
import com.lyl.utils.Response;
import com.lyl.utils.ResponseCode;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.interceptor
 * @ClassName: ResponseResultHandler
 * @Description: 对 返回响应 进行包装 的增强处理
 * @Date: 2020-11-10 13:49
 **/
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 标记位，标记请求的controller类或方法上使用了到了自定义注解，返回数据需要被包装
     */
    public static final String RESPONSE_ANNOTATION = "RESPONSE_ANNOTATION";

    /**
     * 请求中是否包含了 响应需要被包装的标记，如果没有，则直接返回，不需要重写返回体
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest sr = (HttpServletRequest) ra.getRequest();
        // 查询是否需要进行响应包装的标志
        ResponseResult responseResult = (ResponseResult) sr.getAttribute(RESPONSE_ANNOTATION);
        return responseResult == null ? false : true;
    }


    /**
     * 对 响应体 进行包装; 除此之外还可以对响应体进行统一的加密、签名等
     *
     * @param responseBody       请求的接口方法执行后得到返回值(返回响应)
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        logger.info("返回响应 包装进行中。。。");
        Response response;
        // boolean类型时判断一些数据库新增、更新、删除的操作是否成功
        if (responseBody instanceof Boolean) {
            if ((Boolean) responseBody) {
                response = new Response(responseBody, ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.message());
            } else {
                response = new Response(responseBody, ResponseCode.ERROR.code(), ResponseCode.ERROR.message());
            }
        } else {
            // 判断像查询一些返回数据的情况，查询不到数据返回 null;
            if (null != responseBody) {
                response = new Response(responseBody, ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.message());
            } else {
                response = new Response(responseBody, ResponseCode.ERROR.code(), ResponseCode.ERROR.message());
            }
        }
        return response;
    }
}
