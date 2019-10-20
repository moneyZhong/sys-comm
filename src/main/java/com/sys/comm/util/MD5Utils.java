package com.sys.comm.util;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Author:Starry
 * @Description:
 * @Date:Created in 9:46 2018/4/13
 * Modified By:
 */
public class MD5Utils {

    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    
    /**
  	 * 利用反射生成map
  	 * 
  	 * @param obj
  	 * @return
  	 * @throws Exception
  	 */
  	public static Map<String, Object> tranferMapByAnno(Object obj)  {
  		Map<String, Object> mapValue = new HashMap<String, Object>();
  		try {
  			Class<?> cls = obj.getClass();
  			Field[] fields = cls.getDeclaredFields();
  			for (Field field : fields) {
  					field.setAccessible(true);
  					if(field.get(obj)!=null && !"".equals(field.get(obj))){
  						mapValue.put(field.getName(), field.get(obj));
  					}
  			}
  		}catch(Exception e){
  		}
  		
  		return mapValue;
  	}

  	public static String generateString(Object obj) {
  		StringBuffer buff = new StringBuffer();
  		try {
  			Map<String, Object> param = tranferMapByAnno(obj);
  			if (param != null && param.size() > 0) {
  				for (Entry<String, Object> entry : param.entrySet()) {
  					buff.append(entry.getKey() + "=" + entry.getValue() + "&");
  				}
  				buff = buff.deleteCharAt(buff.length() - 1);
  			}
  		} catch (Exception e) {
  			// TODO: handle exception
  		}
  		return buff.toString();
  	}
  	
 	
}
