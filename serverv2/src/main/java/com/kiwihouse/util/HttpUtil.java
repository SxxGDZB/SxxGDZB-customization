package com.kiwihouse.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HttpUtil {
    public static void main(String[] args) {

    }

    public static Map<String,String> postMap(String json, String url, List<BasicHeader> headers) {
        Map<String, String> resultMap = new ConcurrentHashMap<>();
        HttpPost post = new HttpPost(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
//			post.setHeader("Content-Type", "application/json;charset=utf-8");
            for (BasicHeader header : headers) {
                post.addHeader(header);
            }

            StringEntity postingString = new StringEntity(json, "utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);

            Header[] headersR = response.getAllHeaders();
            for (Header header : headersR) {
                resultMap.put(header.getName(), header.getValue());
            }

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            resultMap.put("response", strber.toString());
            resultMap.put("responseCode", String.valueOf(response.getStatusLine().getStatusCode()));

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            post.abort();
        }
        return resultMap;
    }
    public static InputStream getInputSteam(String url, List<BasicHeader> headers,Map<String,String> headerR) {
        InputStream result = null;
        HttpGet get = new HttpGet(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
//			post.setHeader("Content-Type", "application/json;charset=utf-8");
            for (BasicHeader header : headers) {
                get.addHeader(header);
            }

//            StringEntity postingString = new StringEntity(json, "utf-8");
//            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(get);

            Header[] headersR = response.getAllHeaders();
            for (Header header : headersR) {
                headerR.put(header.getName(), header.getValue());
            }
            headerR.put("responseCode", String.valueOf(response.getStatusLine().getStatusCode()));
            result = response.getEntity().getContent();

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            get.abort();
        }
        return result;
    }

    public static String post(String json, String url, List<BasicHeader> headers) {
        String result = "";
        HttpPost post = new HttpPost(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (BasicHeader header : headers) {
                post.addHeader(header);
            }

            StringEntity postingString = new StringEntity(json, "utf-8");
            post.setEntity(postingString);

            HttpResponse response = httpClient.execute(post);
            StringBuilder strber = new StringBuilder();

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            result = strber.toString();
            if (response.getStatusLine().getStatusCode() != 200) {
                result = "服务器异常";
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            //throw new RuntimeException(e);
            return "请求异常";
        } finally {
            post.abort();
        }
        return result;
    }

    public static String put(String json, String url, List<BasicHeader> headers) {
        String result = "";
        HttpPut put = new HttpPut(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (BasicHeader header : headers) {
                put.addHeader(header);
            }

            StringEntity postingString = new StringEntity(json, "utf-8");
            put.setEntity(postingString);
            HttpResponse response = httpClient.execute(put);
            StringBuilder strber = new StringBuilder();

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            result = strber.toString();
            if (response.getStatusLine().getStatusCode() != 200) {
                result = "服务器异常";
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            put.abort();
        }
        return result;
    }
    public static String delete(String url, List<BasicHeader> headers) {
        String result = "";
        HttpDelete delete = new HttpDelete(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (BasicHeader header : headers) {
                delete.addHeader(header);
            }
            HttpResponse response = httpClient.execute(delete);
            StringBuilder strber = new StringBuilder();

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            result = strber.toString();
//            if (response.getStatusLine().getStatusCode() != 200 ) {
//                result = "服务器异常";
//            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            delete.abort();
        }
        return result;
    }

    public static String get(String url, List<BasicHeader> headers) {
        String result = "";
        HttpGet get = new HttpGet(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (BasicHeader header : headers) {
                get.addHeader(header);
            }

            HttpResponse response = httpClient.execute(get);
            StringBuilder strber = new StringBuilder();

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            result = strber.toString();
//            if (response.getStatusLine().getStatusCode() != 200) {
//                result = "服务器异常";
//            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            get.abort();
        }
        return result;
    }

    /**
     * 需要返回 头部信息的请求
     * key=result 返回内容
     * key=heads 返回请求头信息
     *
     * @param json
     * @param url
     * @param headers
     * @param getHeaders 头名称
     * @return
     */
    public static Map<String, Object> post(String json, String url, List<BasicHeader> headers, List<String> getHeaders) {
        Map<String, Object> resultMap = new ConcurrentHashMap<>();
        Map<String, Object> heads = new ConcurrentHashMap<>();
        String result = "";
        HttpPost post = new HttpPost(url);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            for (BasicHeader header : headers) {
                post.addHeader(header);
            }
            StringEntity postingString = new StringEntity(json, "utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);
            for (String headerName : getHeaders) {
                heads.put(headerName, response.getHeaders(headerName));
            }
            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            result = strber.toString();
            if (response.getStatusLine().getStatusCode() != 200) {
                result = "服务器异常";
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            post.abort();
        }
        resultMap.put("resutl", result);
        resultMap.put("heads", heads);
        return resultMap;
    }



}
