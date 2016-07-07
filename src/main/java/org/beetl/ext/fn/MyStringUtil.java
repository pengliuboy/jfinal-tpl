package org.beetl.ext.fn;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public class MyStringUtil extends StringUtil {

	public String replaceAll(String src, String regex, String replacement) {
		if (null == src || src.trim().isEmpty()) {
			return "";
		}
		return src.replaceAll(regex, replacement);
	}
	
	public String getSuffix(String name) {
		if (null == name || name.isEmpty() 
			|| !name.contains(".") || name.endsWith(".")) {
			return "";
		}
		return name.replaceAll(".*(\\..*)", "$1");
	}
	
	public String truncate(String str, int len) {
		if (null == str) {
			return "";
		}
		
		 //假设字符串所有的字符都是双字节字符，长度仍然小于要截取的长度，直接返回
	    if(str.length() * 2 <= len){
	        return str;
	    }

	    try{
	        byte arrByte[] = str.getBytes("unicode");
	        //[-2,-1,34,56,0,97...]
	        //前两个字节是标志位，对于数字和字母，它的第一位是0，第二位是相应的Ascii码；而汉字的两位都
	        //不为0，这样就可以利用Unicode的规则来截取混合的字符串。

	        int arrLen = arrByte.length;

	        if(len >= arrLen) return str;

	        int length = 0;        //记录当前的字节数
	        int index = 2;        //要截取的字节数
	        //要截取的字节数小于整个数组的字节数并且当前的字节数小于传入的截取长度
	        for(; index < arrLen && length < len; index += 2){
	            //如果是双字节，长度+2
	            if(arrByte[index]!=0){
	                length += 2;
	            }else{
	                length += 1;
	            }
	        }

	        if(index >= arrLen){
	            return str;
	        }

	        return new String(arrByte,0,index,"unicode")+"...";
	    }catch(UnsupportedEncodingException e){
	        e.printStackTrace();
	        return str;
	    }
	}
	
	public String byte2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);  
        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        if (returnValue > 1)  
            return (returnValue + "MB");  
        BigDecimal kilobyte = new BigDecimal(1024);  
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        return (returnValue + "KB");
	}
	
}
