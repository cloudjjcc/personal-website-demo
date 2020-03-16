package site.nullpointer.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>类路径 : com.neusoft.a00utils.BeanUtils
 * <p>类描述 : Bean 工具类
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2017-12-20，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public class BeanUtils {
	/**
	 * <p>类路径 : com.neusoft.a00utils.logicEqualLevel
	 * <p>类描述 : 判断相等级别
	 * <p>类详情 : 无
	 * 
	 * @author  王金灿
	 * @version 1.0.0
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2017-12-20，wangjc，创建文件；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	public enum LogicEqualLevel{
		/**
		 * 绝对相等
		 */
		ABSOLUTE_EQUAL, 
		/**
		 * 忽略大小写相等
		 */
		IGNORE_CASE_EQUAL,
		/**
		 * 忽略大小写和下划线相等
		 */
		IGNORE_CASE_AND_UNDERLINE_EQUAL
	}
	   /**
	    * <p>功能描述 : 根据key获取map值，可设置默认值
	    * <p>详细说明 : 无
	    *
	    * @param <E>
	    * @param map
	    * @param key
	    * @param defaultValue
	    * @return 
	    * @author  王金灿
	    *<p>
	    *--------------------------------------------------------------<br>
	    * 修改履历：<br>
	    *        <li> 2017-12-20，wangjc，创建方法；<br>
	    *--------------------------------------------------------------<br>
	    *</p>
	    */ 
	    @SuppressWarnings({ "unchecked", "rawtypes" })  
	    public final static <E> E get(Map map, Object key, E defaultValue) {  
	        Object o = map.get(key);  
	        if (o == null)  
	            return defaultValue;  
	        return (E) o;  
	    }  
	      
	   /**
	    * <p>功能描述 : map集合转bean集合
	    * <p>详细说明 : 无
	    *
	    * @param <T>
	    * @param javaBean
	    * @param mapList
	    * @param level
	    * @return 
	    * @author  王金灿
	    *<p>
	    *--------------------------------------------------------------<br>
	    * 修改履历：<br>
	    *        <li> 2017-12-20，wangjc，创建方法；<br>
	    *--------------------------------------------------------------<br>
	    *</p>
	    */
	    @SuppressWarnings({ "rawtypes" })  
	    public static <T> List<T> mapListToBeanList(T javaBean, List<Map> mapList,LogicEqualLevel level) {  
	    	List<T> objectList = new ArrayList<T>(); 
	        if(mapList == null || mapList.isEmpty()){  
	            return objectList;  
	        }  
	        T object = null;  
	        for(Map map : mapList){  
	            if(map != null){  
	                object = mapToBean(javaBean, map,level);  
	                objectList.add(object);  
	            }  
	        }  
	          
	        return objectList;  
	          
	    }  
	    /**
	     * <p>功能描述 : map集合转bean集合
	     * <p>详细说明 : 无
	     *
	     * @param <T>
	     * @param t
	     * @param list
	     * @return 
	     * @author  王金灿
	     *<p>
	     *--------------------------------------------------------------<br>
	     * 修改履历：<br>
	     *        <li> 2017-12-20，wangjc，创建方法；<br>
	     *--------------------------------------------------------------<br>
	     *</p>
	     */
	    public static <T> List<T> mapListToBeanList(T t, List<Map> list) { 
	    	return mapListToBeanList(t, list,LogicEqualLevel.ABSOLUTE_EQUAL);
	    }
	    /**
	     * <p>功能描述 : map转bean
	     * <p>详细说明 : 无
	     *
	     * @param <T>
	     * @param javaBean
	     * @param map
	     * @param level
	     * @return 
	     * @author  王金灿
	     *<p>
	     *--------------------------------------------------------------<br>
	     * 修改履历：<br>
	     *        <li> 2017-12-20，wangjc，创建方法；<br>
	     *--------------------------------------------------------------<br>
	     *</p>
	     */
	    @SuppressWarnings({ "rawtypes","unchecked", "hiding" })  
	    public static <T> T mapToBean(T javaBean, Map map,LogicEqualLevel level) {  
	        try {  
	            // 获取javaBean属性  
	            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());  
	            // 创建 JavaBean 对象  
	            Object obj = javaBean.getClass().newInstance();  
	  
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	            if (propertyDescriptors != null && propertyDescriptors.length > 0) {  
	                String propertyName = null; // javaBean属性名  
	                Object propertyValue = null; // javaBean属性值  
	                for (PropertyDescriptor pd : propertyDescriptors) {  
	                    propertyName = pd.getName();  
	                    String key=getMapKeyLogicContain(map, propertyName, level);
	                    if (key!=null) {  
	                        propertyValue = map.get(key);  
	                        pd.getWriteMethod().invoke(obj, propertyValue);
	                    }  
	                }  
	                return (T) obj;  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	  
	        return null;  
	    } 
	    /**
	     * <p>功能描述 : map转bean
	     * <p>详细说明 : 无
	     *
	     * @param <T>
	     * @param javaBean
	     * @param map
	     * @return 
	     * @author  王金灿
	     *<p>
	     *--------------------------------------------------------------<br>
	     * 修改履历：<br>
	     *        <li> 2017-12-20，wangjc，创建方法；<br>
	     *--------------------------------------------------------------<br>
	     *</p>
	     */
	    public static <T> T mapToBean(T javaBean, Map map) {
	    	return mapToBean(javaBean, map,LogicEqualLevel.ABSOLUTE_EQUAL);
	    }
	   /**
	    * <p>功能描述 : bean转map
	    * <p>详细说明 : 无
	    *
	    * @param javaBean
	    * @return 
	    * @author  王金灿
	    *<p>
	    *--------------------------------------------------------------<br>
	    * 修改履历：<br>
	    *        <li> 2017-12-20，wangjc，创建方法；<br>
	    *--------------------------------------------------------------<br>
	    *</p>
	    */  
	    @SuppressWarnings({ "rawtypes", "unchecked" })  
	    public static Map beanToMap(Object javaBean) {  
	        Map map = new HashMap();  
	           
	        try {  
	            // 获取javaBean属性  
	            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());  
	  
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	            if (propertyDescriptors != null && propertyDescriptors.length > 0) {  
	                String propertyName = null; // javaBean属性名  
	                Object propertyValue = null; // javaBean属性值  
	                for (PropertyDescriptor pd : propertyDescriptors) {  
	                    propertyName = pd.getName();  
	                    if (!propertyName.equals("class")) {  
	                        Method readMethod = pd.getReadMethod();  
	                        propertyValue = readMethod.invoke(javaBean);
	                        map.put(propertyName, propertyValue);  
	                    }  
	                }  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }    
	        return map;  
	    } 
	    /**
	     * <p>功能描述 : 是否逻辑相等
	     * <p>详细说明 : 无
	     *
	     * @return 
	     * @author  王金灿
	     *<p>
	     *--------------------------------------------------------------<br>
	     * 修改履历：<br>
	     *        <li> 2017-12-20，wangjc，创建方法；<br>
	     *--------------------------------------------------------------<br>
	     *</p>
	     */
	    private static boolean isLogicEqual(String a,String b,LogicEqualLevel level){
	    	if(level.equals(LogicEqualLevel.ABSOLUTE_EQUAL)){
                return a.equals(b);
            }
	    	if(level.equals(LogicEqualLevel.IGNORE_CASE_EQUAL)){
                return a.equalsIgnoreCase(b);
            }
	    	if(level.equals(LogicEqualLevel.IGNORE_CASE_AND_UNDERLINE_EQUAL)){
                return a.replace("_", "").equalsIgnoreCase(b.replace("_", ""));
            }
	    	return false;
	    }
	    /**
	     * <p>功能描述 : 判断map是否逻辑包含key
	     * <p>详细说明 : 无
	     *
	     * @param map
	     * @param a
	     * @param level
	     * @return 
	     * @author  王金灿
	     *<p>
	     *--------------------------------------------------------------<br>
	     * 修改履历：<br>
	     *        <li> 2017-12-20，wangjc，创建方法；<br>
	     *--------------------------------------------------------------<br>
	     *</p>
	     */
	    private static String getMapKeyLogicContain(Map map,String a ,LogicEqualLevel level){
	    	for (Object key : map.keySet()) {
				if(isLogicEqual(a, key.toString(), level)){
					return key.toString();
				}
			}
	    	return null;
	    }
	    //test
	    public static void main(String[] args) {
			
	    	
		}
	} 

