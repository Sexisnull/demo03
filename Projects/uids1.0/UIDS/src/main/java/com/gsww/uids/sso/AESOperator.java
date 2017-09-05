package com.gsww.uids.sso;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加密算法
 * 
 * @author taolm
 *
 */
public class AESOperator {

	/**
	 * 私有构造函数
	 */
	private AESOperator() {		  
    } 
	
	/**
	 * 加密
	 * 
	 * @param secret
	 * @param salt
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String secret, String salt, String plaintext ) throws Exception {  
		
		// 密码
        SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes(), "AES");  
        
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
        IvParameterSpec iv = new IvParameterSpec(salt.getBytes());
        
        // 加密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);            
        byte[] encrypted = cipher.doFinal(plaintext.getBytes("utf-8"));  
        
        // 此处使用BASE64做转码
        String cipherText = new String(Base64.encodeBase64(encrypted));
          
        return cipherText;  
                  
    }  
	
	/**
	 * 解密
	 * 
	 * @param secret
	 * @param salt
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
    public static String decrypt(String secret, String salt, String ciphertext ) throws Exception {  
        
    	 SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes("ASCII"), "AES");  
          
         IvParameterSpec iv = new IvParameterSpec(salt.getBytes());
         
         // 先用base64解密  
         byte[] base64Decrypted = Base64.decodeBase64(ciphertext.getBytes());
         
         // 解密
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
         cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
         byte[] plainTextBytes = cipher.doFinal(base64Decrypted); 
         
         String plainText = new String(plainTextBytes, "utf-8");  
         return plainText;  
    }
}