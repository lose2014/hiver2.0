package com.seaway.android.sdk.upload.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.annotation.SuppressLint;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.upload.util.PictureUtil;
import com.seaway.hiver.model.common.Application;

@SuppressLint("TrulyRandom")
public class UploadSocketHttpReuqest {
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String PREFIX = "--", LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

    /**
     * 上传字符串和图片
     * @param requestURL
     * @param paramMap  字符串参数
     * @param fileMap  图片参数
     * @return
     */
    public static String doHttpPost(String requestURL,HashMap<String,String> paramMap,HashMap<String,String> fileMap){
        if(paramMap==null && fileMap==null){
            return null;
        }
        URL url = null;
        HttpURLConnection conn = null;
        Application app = (Application) Application.getInstance();
        try {
            Logger.d("httpURL:"+requestURL);
            Logger.d("paramMap:"+paramMap+"  fileMap:"+fileMap);
            
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {  
                public X509Certificate[] getAcceptedIssuers(){return null;}  
                public void checkClientTrusted(X509Certificate[] certs, String authType){}  
                public void checkServerTrusted(X509Certificate[] certs, String authType){}  
            }};  
            
            HttpsURLConnection.setDefaultHostnameVerifier(new UploadTrustManager()); 
            SSLContext sc = SSLContext.getInstance("TLS");  
            sc.init(null, trustAllCerts, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory()); 
            
            url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("clientType", "2");
            
            if (null != app.loginVo) {
            	conn.setRequestProperty("accessToken", app.loginVo.getAccessToken());
            	conn.setRequestProperty("userId", "" + app.loginVo.getTeacherId());
			} else {
				conn.setRequestProperty("accessToken", "");
            	conn.setRequestProperty("userId", "");
			}
            
            
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            Logger.d("out:"+out);
            //传字符串参数
            if(paramMap!=null){
                StringBuffer paramSB = new StringBuffer();
                Iterator<Map.Entry<String,String>> iterator= paramMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String,String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Logger.d("key:"+key+"    value:"+value);
                    if(value==null){
                        continue;
                    }
                    paramSB.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    paramSB.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
                    paramSB.append(value);
                    paramSB.append(LINE_END);
                }

                out.write(paramSB.toString().getBytes());
            }
            //传图片
            if(fileMap!=null){
                Iterator<Map.Entry<String,String>> iterator= fileMap.entrySet().iterator();
                int i = 0;
                while (iterator.hasNext()){
                    Map.Entry<String,String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Logger.e("key:"+key+"    value:"+value);
                    if(value==null){
                        continue;
                    }

//                    File file = new File(value);
//                    Logger.e("file大小：" + file.length());
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
//                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""
//                            + file.getName() + "\"" + LINE_END);
//                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""
//                          + value.hashCode() + "\"" + LINE_END);
                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""
                    		+ i + "\"" + LINE_END);
                    sb.append("Content-Type: image/jpeg"
                            + LINE_END);
                    sb.append(LINE_END);

                    out.write(sb.toString().getBytes());
                    
//                    InputStream is = new FileInputStream(file);
                    //图片进行压缩处理
                    InputStream is = PictureUtil.bitmapToInputStream(value);

                    Logger.d("is 大小：" + is.available());
                    Logger.e("is 大小：" + value);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    is.close();
                    out.write(LINE_END.getBytes());
                    
                    i++;
                }
            }

            

            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            out.write(end_data);
            out.flush();
            out.close();
            int res = conn.getResponseCode();
            Logger.d("------返回状态码 res="+res);
            if (res == 200) {
                StringBuffer resStringBuffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                // 处理响应流，必须与服务器响应流输出的编码一致
                responseReader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    resStringBuffer.append(readLine).append("\n");
                }
                String result = resStringBuffer.toString();
                Logger.d("上传文件成功返回："+result);
                responseReader.close();
                return result;
            } else {
            	StringBuffer resStringBuffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                // 处理响应流，必须与服务器响应流输出的编码一致
                responseReader = new BufferedReader(new InputStreamReader(
                        conn.getErrorStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    resStringBuffer.append(readLine).append("\n");
                }
                String result = resStringBuffer.toString();
                Logger.e("上传文件失败返回："+result);
                responseReader.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return FAILURE;
    }
    
    /**
     * 上传字符串和图片
     * @param requestURL
     * @param paramMap  字符串参数
     * @param fileMap  图片参数
     * @param type  上传图片类型 0=证件照，1=商品照片，2=其他照片
     * @return
     */
    public static String doHttpPost(String requestURL,HashMap<String,String> paramMap,HashMap<String,String> fileMap,int type){
        if(paramMap==null && fileMap==null){
            return null;
        }
        System.setProperty("http.keepAlive", "false");

        URL url = null;
        HttpURLConnection conn = null;
        Application app = (Application) Application.getInstance();
        try {
            Logger.d("httpURL:"+requestURL);
            Logger.d("paramMap:"+paramMap+"  fileMap:"+fileMap);

            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {  
                public X509Certificate[] getAcceptedIssuers(){return null;}  
                public void checkClientTrusted(X509Certificate[] certs, String authType){}  
                public void checkServerTrusted(X509Certificate[] certs, String authType){}  
            }};  
            
            HttpsURLConnection.setDefaultHostnameVerifier(new UploadTrustManager()); 
            SSLContext sc = SSLContext.getInstance("TLS");  
            sc.init(null, trustAllCerts, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory()); 
            
            url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("clientType", "2");
            
            if (null != app) {
            	conn.setRequestProperty("accessToken", app.loginVo.getAccessToken());
            	conn.setRequestProperty("userId", "" + app.loginVo.getTeacherId());
			} else {
				conn.setRequestProperty("accessToken", "");
            	conn.setRequestProperty("userId", "");
			}
            
            
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            Logger.d("out:"+out);
            //传字符串参数
            if(paramMap!=null){
                StringBuffer paramSB = new StringBuffer();
                Iterator<Map.Entry<String,String>> iterator= paramMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String,String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Logger.e("key:"+key+"    value:"+value);
                    if(value==null){
                        continue;
                    }
                    paramSB.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    paramSB.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
                    paramSB.append(value);
                    paramSB.append(LINE_END);
                }

                out.write(paramSB.toString().getBytes());
            }
            //传图片
            if(fileMap!=null){
                Iterator<Map.Entry<String,String>> iterator= fileMap.entrySet().iterator();
                int i = 0;
                while (iterator.hasNext()){
                    Map.Entry<String,String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Logger.e("key:"+key+"    value:"+value);
                    if(value==null){
                        continue;
                    }

//                    File file = new File(value);
//                    Logger.e("file大小：" + file.length());
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
//                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""
//                            + file.getName() + "\"" + LINE_END);
//                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""
//                          + value.hashCode() + "\"" + LINE_END);
                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""
                    		+ i + "\"" + LINE_END);
                    sb.append("Content-Type: image/jpeg"
                            + LINE_END);
                    sb.append(LINE_END);

                    out.write(sb.toString().getBytes());
                    
//                    InputStream is = new FileInputStream(file);
                    InputStream is =null;
                    if(type==0){//0=证件照，1=商品照片，2=视频
                    	is = PictureUtil.bitmapToInputStream(value,100,720,1280);
                    }else if(type==1){
                    	is = PictureUtil.bitmapToInputStreamCrop(value, 60, 720, 720);
                    }else {
//                    	is = PictureUtil.bitmapToInputStream(value);
                        Logger.d("is-----------：开始读取视频信息" );
                        is = new FileInputStream(new File(value));
                    }
                    
                    Logger.d("is 大小：" + is.available());
                    Logger.e("is 大小：" + value);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    is.close();
                    out.write(LINE_END.getBytes());
                    
                    i++;
                }
            }

            

            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            out.write(end_data);
            out.flush();
            out.close();
            int res = conn.getResponseCode();
            Logger.d("------shangchuang  chenggong res="+res);
            if (res == 200) {
                StringBuffer resStringBuffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                // 处理响应流，必须与服务器响应流输出的编码一致
                responseReader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    resStringBuffer.append(readLine).append("\n");
                }
                String result = resStringBuffer.toString();
                Logger.d("上传文件成功返回："+result);
                responseReader.close();
                return result;
            } else {
            	StringBuffer resStringBuffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                // 处理响应流，必须与服务器响应流输出的编码一致
                responseReader = new BufferedReader(new InputStreamReader(
                        conn.getErrorStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    resStringBuffer.append(readLine).append("\n");
                }
                String result = resStringBuffer.toString();
                Logger.e("上传文件失败返回："+result);
                responseReader.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return FAILURE;
    }
}