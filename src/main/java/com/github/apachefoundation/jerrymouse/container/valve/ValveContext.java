package com.github.apachefoundation.jerrymouse.container.valve;

import com.github.apachefoundation.jerrymouse.http.HttpRequest;
import com.github.apachefoundation.jerrymouse.http.HttpResponse;

/**
 * @Author: xiantang
 * @Date: 2019/5/24 20:08
 */
public interface ValveContext {
    public String getInfo();

    public void invokeNext(HttpRequest request, HttpResponse response);
}