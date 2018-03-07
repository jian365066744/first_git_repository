package com.ronglian.qd_qrcode.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

import io.shardingjdbc.core.keygen.DefaultKeyGenerator;

/**
 * 应用工具类
 */
public class AppUtils {
	private static Logger LOG = Logger.getLogger(AppUtils.class);
	
	private static int num = 0;
	
	/**
	 * 生成批次号
	 * @return String
	 */
	public static String createSN(String prefix) {	
		String postfix = DateUtils.getDate("yyyyMMddHHmmss");
		return createSN(prefix, postfix, null);
	}
	
	public synchronized static String createSN(String prefix,String postfix,Integer length) {
		if (num >= 99) {
			num = 0;
		}
		num++;
		String result = prefix + String.format("%1$02d", num) + postfix;
		result = (null != length && result.length()>length)?result.substring(0,length):result;
		return result;
	}
	
	public static void pringAjaxData(String result, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			LOG.error("ajax response IOException"+e.getMessage());
		}finally{
			if(out != null){
				out.print(result);
				out.flush();
				out.close();
			}
			out = null;
		}
	}
	
	/**
	 * 产生一个固定返回的随机数
	 * @param start
	 * @param end
	 * @return int
	 */
	public static int getRandom(int start,int end){   
        if(start>end || start <0 || end <0){   
            return -1;   
        }   
        return (int)(Math.random()*(end-start+1))+start;   
    }
	
	  /**
     * 把map转换成Json
     * @param objlist
     * @return
     */
	public  static String objlist2json(List<Map<String, Object>> objlist) {
		StringBuffer buf = new StringBuffer("");
		buf.append("{");
		for (Map<String, Object> map : objlist) {
			for (String key : map.keySet()) {
				buf.append("\"").append(key).append("\"").append(":").append(
						"\"").append(map.get(key)).append("\"").append(",");
			}
		}
		buf.deleteCharAt(buf.lastIndexOf(","));
		buf.append("}");

		return buf.toString();
	}
	
	public  static String Map2json(Map<String, Object> map) {
		StringBuffer buf = new StringBuffer("");
		buf.append("{");
		
			for (String key : map.keySet()) {
				Object value = map.get(key);
				if(value instanceof List){
					buf.append("\"").append(key).append("\"").append(":").append("[");
					for(Map<String, Object> row: (List<Map<String, Object>>)value){
						String json = Map2json(row);
						buf.append(json).append(",");
					}
					buf.deleteCharAt(buf.lastIndexOf(","));
					buf.append("]").append(",");
				} else if(value instanceof String[]) {
					buf.append("\"").append(key).append("\"").append(":").append("[");
					for(String row: (String[])value){
						buf.append("\"").append(row).append("\"").append(",");
					}
					buf.deleteCharAt(buf.lastIndexOf(","));
					buf.append("]").append(",");
				} else {
					buf.append("\"").append(key).append("\"").append(":").append(
							"\"").append(map.get(key)).append("\"").append(",");
				}
			}
		
		buf.deleteCharAt(buf.lastIndexOf(","));
		buf.append("}");

		return buf.toString();
	}
	
	
	/**
	 * 生成随机两位数字加字母
	 * @param param
	 * @return
	 */
	public static String randomStr (String param){
		Random r = new Random();
		String code = "";
		for (int i = 0; i < 3; ++i) {
			// 偶数位生产随机整数
			if (i % 2 == 0) 
			{
				code = code + r.nextInt(10);
				} else
					// 奇数产生随机字母
					{
					int temp = r.nextInt(52);
					char x = (char) (temp < 26 ? temp + 97 : (temp % 26) + 97);
					code += x;
					}
			}
		return code;	
	}

	/**
	 * 金额元转分
	 * @param amount
	 * @author MCZhang
	 * @return
	 */
	public static String amountChange(String amount) {
		BigDecimal bd = new BigDecimal(amount);
		BigDecimal mul = new BigDecimal("100");
		return bd.multiply(mul).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 
	 * compile:正则表达式匹配. <br/>
	 *
	 * @author ZhangMianCai
	 * @param regex 正则表达式
	 * @param input 输入值
	 * @return 是否匹配
	 * @since JDK 1.6
	 */
	public static boolean compile(String regex, String input){
		boolean isFind = false ;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()){
			isFind = true;
		}
		return isFind;
		
	}
	
	
	public static int bytesToInt(byte[] src) {  
		int int_num = 0;
		for (int ii = 0; ii < src.length; ii++) {
			int_num += src[src.length - 1 - ii]
					* (Math.pow(10, ii));
		}
	    return int_num;
    }
	
	//判断是否是通过手机浏览器访问的页面
	public static boolean judgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
	
	/**
	 * 是否为微信浏览器
	 * @param request
	 * @return
	 */
	public static boolean isWeixinBrowser(HttpServletRequest request){
		boolean isWeixinBrowser = false;
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua != null && ua.contains("micromessenger")) {
			isWeixinBrowser = true;
		}
		return isWeixinBrowser;
	}
	
	/**
	 * 支付宝浏览器
	 */
	public static boolean isAalipay(HttpServletRequest request){
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua != null && ua.contains("alipay")) {
		        return true;
	    }else{
	        return false;
	    }
	}
	
	public static String getRequestBody(HttpServletRequest request) throws IOException{
		InputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		String content = "";
		try {
			String line = null;
			while(null != (line = bufferedReader.readLine())){
				content = content + line;
			}
		} catch (Exception e) {
			LOG.error("getRequestBody error",e);
		} finally {
			bufferedReader.close();
			bufferedReader = null;
			inputStream.close();
			inputStream = null;
		}
		return content;
	}
	
	
	//判断是否是汉字
	public static boolean isChineseChar(String str){
       boolean temp = false;
       Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
       Matcher m=p.matcher(str);
       if(m.find()){
           temp =  true;
       }
       return temp;
   }
	/**
	 * HGB 20170301
	 * 初始化随机数种子(电话号码+时间)，生成随机数
	 */
	public static String randomGenerator(){
		String vCode = "";
		long t = System.currentTimeMillis();
		Random random = new Random(t);
		for(int i = 0;i<6;i++){
			vCode = vCode + random.nextInt(10);
		}
		return vCode;
	}
	
	/**
	  * 格式化字符串
	  * @param
	  * @return String
	  * @param formatStr 被格式化字符串
	  * @param tag 不足位补充字符串
	  * @param len 字符串长度
	  * @param direction 1：左补，0：右补
	  * @return desc
	  */
	public static String format(Object formatStr, String tag, int len,int direction) {
		String str = "";
		if (null != formatStr) {
			str = formatStr.toString();
		}
		if (len == 0) {
			return str;
		}
		if (len <= str.length()) {
			return str.substring(0, len);
		}
		StringBuilder tempStr = new StringBuilder();
		for (int i = 0; i < len - str.getBytes().length; i++) {
			tempStr.append(tag);
		}
		if (direction == 0) {
			return str + tempStr;
		} else {
			return tempStr.append(formatStr).toString();
		}
	}
	
	
	static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }
	
	public static final long EPOCH;
    
    private static final long SEQUENCE_BITS = 12L;
    
    private static final long WORKER_ID_BITS = 10L;
    
    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;
    
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;
    
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;
    
    
    private static long sequence;
    
    private static long lastTime;
    
    private static long workerId;
    
    public static void setWorkerId(final long workerId) {
        Preconditions.checkArgument(workerId >= 0L && workerId < WORKER_ID_MAX_VALUE);
        AppUtils.workerId = workerId;
    }
    
    private static long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }
	synchronized public static Number generatorKey() {
		long currentMillis = System.currentTimeMillis();
        Preconditions.checkState(lastTime <= currentMillis, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", lastTime, currentMillis);
        if (lastTime == currentMillis) {
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        lastTime = currentMillis;
        if (LOG.isDebugEnabled()) {
            LOG.info("{}-{}-{}"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTime))+workerId+sequence);
        }
        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
	}
	
	
	public static void main(String[] args) {
//		DefaultKeyGenerator dkg = new DefaultKeyGenerator();
		Long idKey = (Long)generatorKey();
		LOG.info("生成的id为:"+idKey);
		System.out.println("生成的id为:"+idKey);
	}
	
	 
}