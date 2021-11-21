package com.example.demo.controller;

import com.example.demo.main.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 主要测试了Controller层评论的相关功能，包括：
 * 1.用户评价
 * 2.获取商品评价
 * @author 张子文
 *
 */

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class CommentControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CarControllerTest.class);

    
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void addcomment(){//用户评价
    	String path = "http://454121ao63.qicp.vip/addcomment";
    	HashMap<String, String> Headers = new HashMap<>();
    	HashMap<String, String> formMap = new HashMap<>();
    	formMap.put("id", "admin");
    	formMap.put("word", "123");
    	formMap.put("goodid", "0538c81e-f3de-4209-a4a4-c6a5ee6ea118");
    	formMap.put("text","testUserComment");
    	formMap.put("rate","null");
    	
    	String result = "";
        HttpURLConnection connection = null;
        String boundary = "--------------------------132183525382215881770481";//HTTP协议
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 不使用缓存
            connection.setUseCaches(false);
            if (Headers != null) {
                if (Headers.size() > 0) {
                    for (Map.Entry<String, String> entry : Headers.entrySet()) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }
            StringBuffer formSB = new StringBuffer();
            if (formMap != null) {
                if (formMap.size() > 0) {
                    for (Map.Entry<String, String> entry : formMap.entrySet()) {
                        String inputName = entry.getKey();
                        String inputValue = entry.getValue();
                        formSB.append("\r\n").append("--").append(boundary).append("\r\n");
                        formSB.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        formSB.append(inputValue);
                    }
                    formSB.append("\r\n").append("--").append(boundary).append("--");
                }
            }
            connection.connect();
            //OutputStream out = new DataOutputStream(connection.getOutputStream());
            PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            out.print(formSB.toString());
            out.flush();
            //获得响应状态
            int resultCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                formSB = new StringBuffer();
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    formSB.append(readLine).append("\n");
                }
                responseReader.close();
                result = formSB.toString();
            } else {
                result = "{\"code\":\"" + resultCode + "\"}";
            }
            out.close();
        } catch (Exception e) {
            logger.error(e.toString());
            
        } finally {
            connection.disconnect();
        }
    	System.out.println(result);
    	
    }

    @Test
    public void comment(){//获取商品评价
    	String path = "http://454121ao63.qicp.vip/comment";
    	HashMap<String, String> Headers = new HashMap<>();
    	HashMap<String, String> formMap = new HashMap<>();
    	formMap.put("goodid", "0538c81e-f3de-4209-a4a4-c6a5ee6ea118");
    	String result = "";
        HttpURLConnection connection = null;
        String boundary = "--------------------------132183525382215881770481";//HTTP协议
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 不使用缓存
            connection.setUseCaches(false);
            if (Headers != null) {
                if (Headers.size() > 0) {
                    for (Map.Entry<String, String> entry : Headers.entrySet()) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }
            StringBuffer formSB = new StringBuffer();
            if (formMap != null) {
                if (formMap.size() > 0) {
                    for (Map.Entry<String, String> entry : formMap.entrySet()) {
                        String inputName = entry.getKey();
                        String inputValue = entry.getValue();
                        formSB.append("\r\n").append("--").append(boundary).append("\r\n");
                        formSB.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        formSB.append(inputValue);
                    }
                    formSB.append("\r\n").append("--").append(boundary).append("--");
                }
            }
            connection.connect();
            //OutputStream out = new DataOutputStream(connection.getOutputStream());
            PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            out.print(formSB.toString());
            out.flush();
            //获得响应状态
            int resultCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                formSB = new StringBuffer();
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    formSB.append(readLine).append("\n");
                }
                responseReader.close();
                result = formSB.toString();
            } else {
                result = "{\"code\":\"" + resultCode + "\"}";
            }
            out.close();
        } catch (Exception e) {
            logger.error(e.toString());
            
        } finally {
            connection.disconnect();
        }
    	System.out.println(result);
    	
    	
    }

    @Test
    public void uc(){
    	String path = "http://454121ao63.qicp.vip/uc";
    	HashMap<String, String> Headers = new HashMap<>();
    	HashMap<String, String> formMap = new HashMap<>();
    	formMap.put("id", "admin");
    	formMap.put("word", "0538c81e-f3de-4209-a4a4-c6a5ee6ea118");
    	String result = "";
        HttpURLConnection connection = null;
        String boundary = "--------------------------132183525382215881770481";//HTTP协议
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 不使用缓存
            connection.setUseCaches(false);
            if (Headers != null) {
                if (Headers.size() > 0) {
                    for (Map.Entry<String, String> entry : Headers.entrySet()) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }
            StringBuffer formSB = new StringBuffer();
            if (formMap != null) {
                if (formMap.size() > 0) {
                    for (Map.Entry<String, String> entry : formMap.entrySet()) {
                        String inputName = entry.getKey();
                        String inputValue = entry.getValue();
                        formSB.append("\r\n").append("--").append(boundary).append("\r\n");
                        formSB.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        formSB.append(inputValue);
                    }
                    formSB.append("\r\n").append("--").append(boundary).append("--");
                }
            }
            connection.connect();
            //OutputStream out = new DataOutputStream(connection.getOutputStream());
            PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            out.print(formSB.toString());
            out.flush();
            //获得响应状态
            int resultCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                formSB = new StringBuffer();
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    formSB.append(readLine).append("\n");
                }
                responseReader.close();
                result = formSB.toString();
            } else {
                result = "{\"code\":\"" + resultCode + "\"}";
            }
            out.close();
        } catch (Exception e) {
            logger.error(e.toString());
            
        } finally {
            connection.disconnect();
        }
    	System.out.println(result);
    	
    	
    }
}








