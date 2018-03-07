package com.ronglian.qd_qrcode.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/** 
*字符串 DESede(3DES) 加密 
*/ 
public class TripleDES {
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish 
	private static final String hexString = "0123456789ABCDEF"; 
	private static final int formatKeyLength = 24;
	private static final String DesCharset = "UTF-8";// 加密算法字符集
     
 	/**
 	 * Triple密钥
 	 */
 	public static final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte)0x8F, 0x10, 
         0x40, 0x31, 0x28, 0x2A, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 
         0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x35, 0x40, 0x36, 
        (byte) 0xE2 
        };	
 	
     /** 
      *                                                    
      * @param keybyte  加密密钥，长度为24字节 
      * @param src     字节数组(根据给定的字节数组构造一个密钥。 ) 
      * @return 
      */ 
     public static String encryptMode(byte[] keybyte, byte[] src) {
         return encode(encrypt(keybyte, src, Algorithm, Algorithm)); 
     }
     
     public static String encryptMode(byte[] keybyte, byte[] src, String algorithm, String algorithmInstance) {
    	 return encode(encrypt(keybyte, src, algorithm, algorithmInstance));
     }
     
     public static byte[] encrypt(byte[] keybyte, byte[] src){
    	 return encrypt(keybyte, src, Algorithm, Algorithm);
     }
     
     public static byte[] encrypt(byte[] keybyte, byte[] src, String algorithm, String algorithmInstance) {
    	 try { 
             // 根据给定的字节数组和算法构造一个密钥 
             SecretKey deskey = new SecretKeySpec(keybyte, algorithm);
             // 加密 
             Cipher c1 = Cipher.getInstance(algorithmInstance); 
             c1.init(Cipher.ENCRYPT_MODE, deskey); 
             byte[] c2 = c1.doFinal(src); 
             return c2;
         } catch (java.security.NoSuchAlgorithmException e1) { 
             e1.printStackTrace(); 
         } catch (javax.crypto.NoSuchPaddingException e2) { 
             e2.printStackTrace(); 
         } catch (java.lang.Exception e3) { 
             e3.printStackTrace(); 
         } 
         return null; 
     }
     
    /** 
      *  
      * @param keybyte 密钥 
      * @param src       需要解密的数据,即数据库中取出的加密后的密码字段 
     * @return 
      */ 
     public static String decryptMode(byte[] keybyte, String src) {
         return decryptMode(keybyte, src, Algorithm, Algorithm); 
     }
     /**
 	 * 
 	 * DeEnycrptDes: 解密，默认字符集 . <br/>
 	 *
 	 * @author tolly
 	 * @param src
 	 * @param key 24位定长
 	 * @return String
 	 * @throws Exception
 	 */
     public static String decryptMode(String keyStr, String src) 
    		 throws Exception {
    	 keyStr = getKeyLen(keyStr, 24);//生成24位密钥
 		String ret = StringUtils.EMPTY;
 		if (StringUtils.isNotBlank(src)) {
 			//ret = (new String(decode(hexStr2ByteArr(src), key.getBytes(DesCharset))));
 			ret = (new String(decode(hexStr2ByteArr(src), keyStr.getBytes(DesCharset)),DesCharset));//不同平台编码
 		}
 		return ret;
 	}
     
     /*public static String decryptMode(String keyStr, String src) {
    	 keyStr = getKeyLen(keyStr, formatKeyLength);
    		try {
    			byte[] keyByte = keyStr.getBytes(DesCharset);
    			return decryptMode(keyByte, src, Algorithm, Algorithm); 
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
    		return "";
     }*/
     
     public static String decryptMode(byte[] keybyte, String src, String algorithm,String algorithmInstance) { 
         try { 
        	 byte[] sc = decode(src);
             // 生成密钥 
             SecretKey deskey = new SecretKeySpec(keybyte, algorithm); 
             // 解密 
             Cipher c1 = Cipher.getInstance(algorithmInstance); 
             c1.init(Cipher.DECRYPT_MODE, deskey);
             return (new String(c1.doFinal(sc),DesCharset));
              
         } catch (java.security.NoSuchAlgorithmException e1) { 
            e1.printStackTrace(); 
         } catch (javax.crypto.NoSuchPaddingException e2) { 
             e2.printStackTrace(); 
         } catch (java.lang.Exception e3) { 
            e3.printStackTrace(); 
         } 
         return null; 
     } 
     
     /** 
      * 字符串转为16进制 
      * @param str 
      * @return 
      */ 
     public static String encode(byte[] bytes)  
     {  
         //根据默认编码获取字节数组  
 
         StringBuilder sb=new StringBuilder(bytes.length*2);  
          //将字节数组中每个字节拆解成2位16进制整数  
        for(int i=0;i<bytes.length;i++)  
        {  
             sb.append(hexString.charAt((bytes[i]&0xf0)>>4));  
             sb.append(hexString.charAt((bytes[i]&0x0f)>>0));  
         }  
         return sb.toString();  
    }  
     /** 

      *  
      * @param bytes 
      * @return 
      * 将16进制数字解码成字符串,适用于所有字符（包括中文）  
      */ 
     public static byte[] decode(String bytes)  
     {  
    	 
         ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);  
         //将每2位16进制整数组装成一个字节  
         for(int i=0;i<bytes.length();i+=2)  
             baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));  
         return baos.toByteArray();  
     }  
     
     public static byte[] decode(byte[] input, byte[] key)
    			throws Exception {
    			SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
    			Cipher c1 = Cipher.getInstance(Algorithm);
    			c1.init(Cipher.DECRYPT_MODE, deskey);
    			byte[] clearByte = c1.doFinal(input);
    			return clearByte;
    		}

     public static byte[] hexStr2ByteArr(String strIn)
    			throws Exception {
    			byte[] arrB = strIn.getBytes(DesCharset);
    			int iLen = arrB.length;
    			byte[] arrOut = new byte[iLen / 2];
    			
    			for (int i = 0; i < iLen; i = i + 2) {
    				String strTmp = new String(arrB, i, 2);
    				arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    			}
    			return arrOut;
    		}
    
     
     /**
      * 根据key值加密字符串
      * @param puk
      * @param value
      * @return
      */
     public static String encryptMode(String key, String value) {
    	 return encryptMode(key, value, DesCharset);
     }
     
     public static String encryptMode(String key, String value, String charSet) {
    	key = getKeyLen(key, formatKeyLength);
		try {
			byte[] keyByte = key.getBytes(charSet);
			byte[] valByte = value.getBytes(charSet);
			return encode(encrypt(keyByte, valByte, Algorithm, Algorithm)); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
     }
     
 	/**
 	 * 
 	 * getKeyLen: 字符串后固定长度位，不够左补0 . <br/>
 	 * @param key
 	 * @param len
 	 * @return String
 	 */
 	public static String getKeyLen(String key, int len) {
 		String keyLen = StringUtils.EMPTY;
 		if (key.length() >= len) {
 			keyLen = StringUtils.substring(key, key.length() - len);
 		} else {
 			keyLen = StringUtils.leftPad(key, len, "0");
 		}
 		return keyLen;
 	}
 	
 	public static void main(String[] args) {
 		System.out.println("");
 		try {
 			String s = encryptMode("1111","aaaa");
 			System.out.println(decryptMode("1111", s));
 			System.out.println(Integer.parseInt("aa", 16));
 			System.out.println(Arrays.toString("aaaa".getBytes(DesCharset)));
 			byte[] bytes = hexStr2ByteArr("aaaa");
 			System.out.println(Arrays.toString(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 

