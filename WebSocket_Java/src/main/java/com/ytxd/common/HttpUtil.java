package com.ytxd.common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {
	    
    /**
     * 使用HttpClient4.5 post提交multipart/form-data数据实现多文件上传
     * @param url 请求地址
     * @param MultiValueMap<String, MultipartFile> post提交的文件列表
     * @param mapParam 附带的文本参数
     * @param timeout 请求超时时间(毫秒)
     * @return Map<String, String>
     */
     public static Map<String, String> httpPost(String url, MultiValueMap<String, MultipartFile> fileMap, Map<String, Object> mapParam, int timeout) {
         Map<String, String> resultMap = new HashMap<String, String>();
         CloseableHttpClient httpClient = HttpClients.createDefault();
         String result = "";
         try {
         	HttpPost httpPost = new HttpPost(url);
             MultipartEntityBuilder builder = MultipartEntityBuilder.create();
             builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
             builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
             if(fileMap!=null){
	             for (Entry<String, List<MultipartFile>> entry : fileMap.entrySet()) {
	                 List<MultipartFile> multipartFiles = entry.getValue();
	            	 String fileParName = entry.getKey();
	            	 String fileName = null;
	                 MultipartFile multipartFile = null;
	            	 for (int i = 0; i < multipartFiles.size(); i++) {
	                     multipartFile = multipartFiles.get(i);
	                     fileName = multipartFile.getOriginalFilename();
	                     builder.addBinaryBody(fileParName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
	                 }
	             }
             }
             //解决中文乱码
             ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
             for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
                 if(entry.getValue() == null)
                     continue;
                 // 类似浏览器表单提交，对应input的name和value
                 builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
             }
             HttpEntity entity = builder.build();
             httpPost.setEntity(entity);
             HttpResponse response = httpClient.execute(httpPost);// 执行提交

             // 设置连接超时时间
             RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                     .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
             httpPost.setConfig(requestConfig);

             HttpEntity responseEntity = response.getEntity();
             resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
             resultMap.put("data", "");
             if (responseEntity != null) {
                 // 将响应内容转换为字符串
                 result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                 resultMap.put("data", result);
             }
         } catch (Exception e) {
             resultMap.put("scode", "error");
             resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());

//             Writer w = new StringWriter();
//             e.printStackTrace(new PrintWriter(w));
//             logger.error("HTTP请求出现异常: " + w.toString());
         } finally {
             try {
                 httpClient.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return resultMap;
     }

     /**
      * 使用HttpClient4.5 post提交数据
      * @param url 请求地址
      * @param mapParam 附带的文本参数
      * @return String
      */
	public static String httpPost(String url, Map<String, Object> mapParam) {
		String returnString = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
			if(entry.getValue() != null) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			} else {
				formparams.add(new BasicNameValuePair(entry.getKey(), null));
			}
		}

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 调用接口返回的字符串
					returnString = EntityUtils.toString(entity, "UTF-8");
//					jsonobject = JSON.parseObject(responseString);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnString;
	}
	
	/**
	 * 发送post请求，带body和header参数。
	 * @param url url地址
	 * @param mapBody body参数
	 * @param mapHeader header参数
	 * @return 返回json
	 */
	public static String httpPost(String url, Map<String, Object> mapBody, Map<String, Object> mapHeader) {
		String returnString = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建header参数
		for (Map.Entry<String, Object> entry : mapHeader.entrySet()) {
			if(entry.getValue() != null) {
				httppost.addHeader(entry.getKey(), entry.getValue().toString());
			} else {
				httppost.addHeader(entry.getKey(), null);
			}			
		}
		// 创建body参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> entry : mapBody.entrySet()) {
			if(entry.getValue() != null) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			} else {
				formparams.add(new BasicNameValuePair(entry.getKey(), null));
			}			
		}

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 调用接口返回的字符串
					returnString = EntityUtils.toString(entity, "UTF-8");
//					jsonobject = JSON.parseObject(responseString);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnString;
	}
	
	
	
	public static String httpGet(String url, Map<String, Object> mapParam) {
		return httpGet(url, mapParam, null);
	}
	public static String httpGet(String url, Map<String, Object> mapParam, Map<String, Object> mapHeader) {
		String returnString = null;
		CloseableHttpClient httpClient = null;
		try {
			// 通过址默认配置创建一个httpClient实例
	        httpClient = HttpClients.createDefault();
	        //创建URLBuilder
	        URIBuilder uriBuilder = new URIBuilder(url);
	        // 创建Params参数队列
	        if(mapParam != null) {
	        	HttpParams params = new BasicHttpParams();
		 		for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
		 			if(entry.getValue() != null) {
		 				uriBuilder.addParameter(entry.getKey(), entry.getValue().toString());
		 			} else {
		 				uriBuilder.addParameter(entry.getKey(), null);
		 			}			
		 		}
	        }
	        // 创建httpGet远程连接实例
	        HttpGet httpGet = new HttpGet(uriBuilder.build());
	        // 创建header参数
	        if(mapHeader != null) {
	        	for (Map.Entry<String, Object> entry : mapHeader.entrySet()) {
		 			if(entry.getValue() != null) {
		 				httpGet.setHeader(entry.getKey(), entry.getValue().toString());
		 			} else {
		 				httpGet.setHeader(entry.getKey(), null);
		 			}
		 		}
	        }	
	 		
	 		// 设置配置请求参数
	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
	                .setConnectionRequestTimeout(35000)// 请求超时时间
	                .setSocketTimeout(60000)// 数据读取超时时间
	                .build();
	        // 为httpGet实例设置配置
	        httpGet.setConfig(requestConfig);
	        // 执行get请求得到返回对象
	        CloseableHttpResponse response = httpClient.execute(httpGet);
	        try {
		        // 通过返回对象获取返回数据
		        HttpEntity entity = response.getEntity();
		        // 通过EntityUtils中的toString方法将结果转换为字符串
		        if (entity != null) {
					// 调用接口返回的字符串
		        	returnString = EntityUtils.toString(entity, "UTF-8");
				}
	        } catch (IOException e) {
                e.printStackTrace();
            } finally {
				response.close();
			}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnString;
    }

	/**
	 * 使用HttpClient4.5 post提交数据
	 * @param url 请求地址
	 * @param mapParam 附带的文本参数
	 * @return String
	 */
	public static <T> String httpPostUrl(String url, Map<String, T> mapParam) {
		StringBuilder response = new StringBuilder();
		HttpsURLConnection con = null;
		try {
			URL obj = new URL(url);
			 con = (HttpsURLConnection) obj.openConnection();
			// 添加请求头
			con.setHostnameVerifier(new CustomHostnameVerifier());
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 发送 POST 请求
			String urlParameters = "";
			if(mapParam != null && !mapParam.isEmpty()){
				for(Map.Entry<String, T> entry : mapParam.entrySet()){
					if(entry.getValue() != null){
						if(urlParameters.length() == 0){
							urlParameters += entry.getKey() +"="+entry.getValue();
						}else {
							urlParameters += "&"+entry.getKey() +"="+entry.getValue();
						}
					}
				}
			}
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			// 把数据发送到请求正文
			con.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				wr.write(postData);
			}
			// 获取响应代码
			int responseCode = con.getResponseCode();
			// 读取响应
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// 输出结果
				System.out.println(response.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			con.disconnect();
		}catch (Exception e){
			if(con != null ){
				con.disconnect();
			}
			e.printStackTrace();
		}
		return response.toString();
	}
}