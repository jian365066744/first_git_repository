package com.ronglian.qd_qrcode.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: HttpClientUtil <br/>
 * Function: HttpClient工具类 . <br/>
 * @since JDK 1.7
 */
public class HttpClientUtil {
	protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static final int ConTimeOut = 30000;
	private static final int SoTimeOut = 120000;
	private static final String charset = "UTF-8";
	
	/**
	 * get请求
	 */
	public static String httpGet(String url){
		return httpGet(url, charset);
	}
	
	/**
	 * get请求
	 * @param url
	 * @param code
	 * @param connectionTimeout
	 * @param socketTimeout
	 * @return
	 */
	public static String httpGet(String url, String code){
		logger.info("GetPage:" + url);
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(ConTimeOut);
		managerParams.setSoTimeout(SoTimeOut);
		
		GetMethod method = new GetMethod(url);
		String result = null;
		try {
			httpClient.executeMethod(method);
			int status = method.getStatusCode();
			if (status == HttpStatus.SC_OK) {
				result = IOUtils.toString(method.getResponseBodyAsStream(), code);
			} else {
				logger.info("Method failed: " + method.getStatusLine());
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.error("Please check your provided http address!", e);
		} catch (IOException e) {
			// 发生网络异常
			logger.error("发生网络异常！url:"+url, e);
		} finally{
			// 释放连接
			if(method!=null)
				method.releaseConnection();
			if (httpClient != null) {
				try {
					((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				} catch (Exception e) {
					logger.error("-------> Close HTTP connection exception:", e);
				}
			}
			method = null;
			httpClient = null;
		}
		return result;
	}
	
	/**
	 * post请求
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> paramMap) {
		return httpPost(url, paramMap, charset);
	}
	
	/**
	 * httpPost: httpclient post方法 . <br/>
	 * 支持重定向，支持请求转发
	 * 例如：
	 * response.sendRedirect(url);//重定向
	   request.getRequestDispatcher(url).forward(request, response);//请求转发
	 * @param url
	 * @param paramMap
	 * @param code
	 * @return String
	 * @since JDK 1.7
	 */
	public static String httpPost(String url, Map<String, String> paramMap, String code) {
		logger.info("GetPage:" + url);
		String result = null;
		if (url == null || url.trim().length() == 0)
			return null;
		HttpClient httpClient = new HttpClient();
		// 设置header
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
		//设置HttpPost编码
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, code);
		
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(ConTimeOut);
		managerParams.setSoTimeout(SoTimeOut);
		
		// 添加信任链接 by
		if (url.indexOf("https") >= 0) {
			Protocol myhttps = new Protocol("https", new MyProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}
		
		PostMethod method = new PostMethod(url);
		AppendPostParam(method, paramMap);//设置post方法参数
		try {
			int statusCode = httpClient.executeMethod(method);
			
			// 检查是否重定向
			if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				// 读取新的 URL 地址
				Header header = method.getResponseHeader("location");
				if (header != null) {
					String newuri = header.getValue();
					if ((newuri == null) || (newuri.equals("")))
						newuri = "/";
					method = new PostMethod(newuri);
					AppendPostParam(method, paramMap);
					statusCode = httpClient.executeMethod(method);
				}
			}
			logger.info("httpClientUtil::statusCode=" + statusCode +"  "+ method.getStatusLine().toString());
			result = IOUtils.toString(method.getResponseBodyAsStream(), code);
			logger.info("response content:" + result);
		} catch (Exception e) {
			logger.error("time out", e);
		} finally {
			if (method != null){
				method.releaseConnection();
			}
			if (httpClient != null) {
				try {
					((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				} catch (Exception e) {
					logger.error("-------> Close HTTP connection exception:", e);
				}
			}
			method = null;
			httpClient = null;
		}
		return result;
	}
	
	/**
	 * 
	 * httpPost: http post 请求 . <br/>
	 * @param url
	 * @param param 格式key1=value1&key2=value2
	 * @param code字符集编码
	 * @return String
	 */
	public static String httpPost(String url, String param, String code) {
		Map<String, String> paramMap = new HashMap<String, String>();
		if(StringUtils.isBlank(param)){
			return null;
		}
		String[] paramArray = StringUtils.split(param, "&");
		/*for(int i=0;i<paramArray.length;i++){
			logger.info(paramArray[i]);
		}*/
		for (String val : paramArray) {
			String[] valArray = StringUtils.split(val,"=");
			paramMap.put(valArray[0], valArray[1]);
		}
		logger.info("发送的数据:{}", paramMap.toString());
		return httpPost(url, paramMap, code);
	}
	
	/**
	 * AppendPostParam: 设置post方法发送参数 . <br/>
	 * @param method
	 * @param paramMap
	 * @since JDK 1.7
	 */
	private static void AppendPostParam(PostMethod method, Map<String, String> paramMap) {
		if(paramMap != null && !paramMap.isEmpty()){
			Iterator<?> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next() + "";
				Object o = paramMap.get(key);
				if (o != null && o instanceof String) {
					method.addParameter(new NameValuePair(key, o.toString()));
				}
				if (o != null && o instanceof String[]) {
					String[] s = (String[]) o;
					if (s != null)
						for (int i = 0; i < s.length; i++) {
							method.addParameter(new NameValuePair(key, s[i]));
						}
				}
			}
		}
	}
	
	/**
	 * URL有效性检测
	 * @param url
	 * @param code
	 * @return int
	 */
	public static int httpPostTest(String url, String code) {
		logger.info("开始进行URL有效性检测:" + url);
		int statusCode = 0;
		if (url == null || url.trim().length() == 0) {
			return statusCode;
		}
		HttpClient httpClient = new HttpClient();
		// 设置header
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//
		
		// 20120627 by ljyan add 设置HttpPost编码
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, code);
		PostMethod method = new PostMethod(url);
		try {
			statusCode = httpClient.executeMethod(method);
		} catch (Exception e) {
			logger.info("time out");
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
			httpClient = null;
		}
		return statusCode;
	}
}
