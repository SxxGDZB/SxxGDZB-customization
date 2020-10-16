package com.kiwihouse.util.test;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;
 

 

@Slf4j
public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 5000;
    private static final int COON_TIMEOUT = 6000;
    private static final int SOCKET_TIMEOUT = 5000;
    private static Integer MAXTOTAL = 100;
    private static Integer MAXPERROUTE = 20;
    /**
     * 监控和回收空闲连接
     */
    private static IdleConnectionMonitorThread ide;
    /**
     * 发送请求的客户端单例
     */
    private static CloseableHttpClient httpClient;

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置连接池大小
        connMgr.setMaxTotal(MAXTOTAL);
        connMgr.setDefaultMaxPerRoute(MAXPERROUTE);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(COON_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(SOCKET_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
        httpClient = getHttpClient();
        //开启监控线程,对异常和空闲线程进行关闭
        ide = new IdleConnectionMonitorThread(connMgr);
        ide.start();

    }

    private static void setGetParam(StringBuilder param, Map<String, Object> params) {
        int i = 0;
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.size() == i) {
                    return;
                }
                param.append(key).append("=").append(params.get(key));
                i++;
                if (i != params.size()) {
                    param.append("&");
                }
            }
        }
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl  API接口URL
     * @param headers 需要添加的httpheader参数
     * @param params  参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, String> headers, Map<String, Object> params) {
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                httpPost.addHeader(key, headers.get(key));
            }
        }
        try {

            List<NameValuePair> pairList = new ArrayList<>(params.size());
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(),String.valueOf(entry
                            .getValue()));
                    pairList.add(pair);
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            log.info(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 Get 请求（HTTPS），K-V形式
     *
     * @param headers
     * @param params  参数map
     * @return
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        CloseableHttpResponse response = null;
        String apiUrl = url + "?";
        String httpStr = null;
        StringBuilder param = new StringBuilder();
        setGetParam(param, params);
        int i = 0;
        apiUrl += param;
        log.info("url: " + apiUrl);
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            //添加http headers
            if (headers != null && headers.size() > 0) {
                for (String key : headers.keySet()) {
                    httpGet.addHeader(key, headers.get(key));
                }
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("code : " + statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;


    }


    /**
     * 传数据流 返回str
     *
     * @param apiUrl
     * @param headers
     * @param bytes
     * @return
     */
    public static String doPost(String apiUrl, Map<String, String> headers, byte[] bytes) {
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                httpPost.addHeader(key, headers.get(key));

            }
        }
        try {
            if (bytes != null && bytes.length != 0) {
                httpPost.setEntity(new ByteArrayEntity(bytes));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * POST 请求，JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     * @return
     */
    public static String doPost(String apiUrl, Map<String, String> headers, Object json) {
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                httpPost.addHeader(key, headers.get(key));

            }
        }
        try {
            //解决中文乱码问题
            if (json != null) {
                StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    private static CloseableHttpClient getHttpClient() {
        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 5) {
                    //重试超过5次,放弃请求
                    log.error("retry has more than 5 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    log.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException) {
                    // SSL握手异常
                    log.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    //超时
                    log.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // 服务器不可达
                    log.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // 连接超时
                    log.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException) {
                    log.error("SSLException");
                    return false;
                }
                //直接返回true 就是get和post请求都重试
                return true;
            }
        };

        return HttpClients.custom().setConnectionManager(connMgr).setRetryHandler(handler).setDefaultRequestConfig(requestConfig).build();

    }

    public static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager conn;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.conn = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        conn.closeExpiredConnections();
                        conn.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }
    private static String appId = "7V0gATUprx";
    private static String appKey = "qPyWVHtIvu";
    private static String token = "l2ZVaWPHMc";
    public static void main(String[] args) {
        long m = System.currentTimeMillis();
        long s = 86400000L;
        System.out.println(new java.sql.Timestamp(m + s));
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        GetTokenBean getTokenBean = new GetTokenBean();
        getTokenBean.setAppId(appId);
        getTokenBean.setAppKey(appKey);
        doPost("https://124.126.120.102/bot/v1/sip%3A1065988584444012%40botplatform.rcs.vnet.cn/accessToken",headers, getTokenBean);
    }
   }