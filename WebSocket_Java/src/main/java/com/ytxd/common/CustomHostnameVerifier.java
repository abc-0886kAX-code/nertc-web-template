package com.ytxd.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class CustomHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        // 如果主机名匹配您期望的值，例如您的服务器域名，则返回 true。
        if (hostname.equals("111.39.105.92")) {
            return true;
        }

        // 否则，用默认 HostnameVerifier 进行验证
        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
        return hv.verify(hostname, session);
    }
}