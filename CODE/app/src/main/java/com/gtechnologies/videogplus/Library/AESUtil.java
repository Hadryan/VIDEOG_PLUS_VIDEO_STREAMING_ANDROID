package com.gtechnologies.videogplus.Library;
import android.util.Base64;

import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil
{
	private static final String ALGO = "AES";

	public static String decrypt(String encryptedData, String secretKeyPhrase) throws Exception
	{
		Key key = new SecretKeySpec(secretKeyPhrase.getBytes(), AESUtil.ALGO);
		Cipher c = Cipher.getInstance(AESUtil.ALGO);
		c.init(Cipher.DECRYPT_MODE, key);

		byte[] decordedValue = Base64.decode(encryptedData, Base64.DEFAULT);
		byte[] decValue = c.doFinal(decordedValue);

		return new String(decValue);
	}
	
	
	public static String encrypt(String Data, String secretKeyPhrase) throws Exception {
		
		Key key = new SecretKeySpec(secretKeyPhrase.getBytes(), ALGO);
    
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        encryptedValue = URLEncoder.encode(encryptedValue);
        return encryptedValue;
    }
	
	
	public static void main(String[] ar) throws Exception {
		
		
	/*	String result=AESUtil.decrypt(URLDecoder.decode("oPuQ2KteM6TRa6FkpS27Qw%3D%3D"), "BDFHJLNPpnljhfdb");
		
		System.out.println(result);*/
		
		//DPlC02Afc226Cd0eZwtheA%3D%3D
		
		
		String result2=AESUtil.encrypt("3483", "BDFHJLNPpnljhfdb");
		System.out.println(result2);
	}
	
	
	public static String generateMD5Hash(String password){
		try{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] dataBytes = new byte[1024];	
		 md.update(password.getBytes());

	        byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        System.out.println("Digest(in hex format):: " + sb.toString());
	 
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex= Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	System.out.println("Digest(in hex format):: " + hexString.toString());
	    	
	    return	hexString.toString();
	    
		
	}catch(Exception e){
	
	}
		return "0";
	
	}
}	

	