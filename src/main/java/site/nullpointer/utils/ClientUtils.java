package site.nullpointer.utils;

import javax.servlet.http.HttpServletRequest;

public class ClientUtils {
	/**
	 * 获取用户真实ip
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 获取用户代理
	 * @param request
	 * @return
	 */
	public static String getAgentString(HttpServletRequest request){
		return request.getHeader("user-agent");
	}
	public static String getIpOnlyMd5Key(HttpServletRequest request,String key){
		String ip=getIpAddress(request);
		return MD5Utils.string2MD5(ip+"@"+key);
	}
}
