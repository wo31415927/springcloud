package com.springcloud.service.router.example;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER;

/**
 * just a example for routing Filter，not pass test use okHttpClient instead of apache httpClient
 *
 * @author cctv 2018/1/28
 */
//@Component
public class OkHttpRoutingFilter extends ZuulFilter {
  @Autowired private ProxyRequestHelper helper;

  @Override
  public String filterType() {
    return ROUTE_TYPE;
  }

  @Override
  public int filterOrder() {
    // run before PreDecoration
    return SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;//RequestContext.getCurrentContext().getRouteHost() != null && RequestContext.getCurrentContext().sendZuulResponse();
  }

  @Override
  public Object run() {
    OkHttpClient httpClient = new OkHttpClient();

    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();

    String method = request.getMethod();

    String uri = this.helper.buildZuulRequestURI(request);

    Headers.Builder headers = new Headers.Builder();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      Enumeration<String> values = request.getHeaders(name);

      while (values.hasMoreElements()) {
        String value = values.nextElement();
        headers.add(name, value);
      }
    }
    InputStream inputStream = null;
    try {
      inputStream = request.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = null;
    try {

      if (inputStream != null && HttpMethod.permitsRequestBody(method)) {
        MediaType mediaType = null;
        if (headers.get("Content-Type") != null) {
          mediaType = MediaType.parse(headers.get("Content-Type"));
        }
        requestBody = RequestBody.create(mediaType, StreamUtils.copyToByteArray(inputStream));
      }

      Request.Builder builder =
          new Request.Builder().headers(headers.build()).url(uri).method(method, requestBody);

      Response response = httpClient.newCall(builder.build()).execute();

      LinkedMultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
      for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
        responseHeaders.put(entry.getKey(), entry.getValue());
      }

      this.helper.setResponse(response.code(), response.body().byteStream(), responseHeaders);
    } catch (IOException e) {
      e.printStackTrace();
    }
    context.setRouteHost(null); // prevent SimpleHostRoutingFilter from running
    return null;
  }
}
