package cc.nonone.generatesql.common.utils;
import java.util.*;

/**
 * 通用常用字符串、对象、集合工具类
 * @class CommonUtil
 * @author NonOne
 * @modified 2018-8-20 上午11:53:43
 */
@SuppressWarnings("rawtypes")
public class CommonUtil {
	
	/**
	 * 判断字符串是否为空
	 * @param str 验证字符串
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:05:26
	 */
	public static boolean isBlank(String str) {
		if(null == str || "".equals(str) 
			|| str.length() == 0
			|| "".equals(str.replaceAll(" ", "")))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param str 验证字符串
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:05:26
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str 验证字符串
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:05:26
	 */
	public static boolean isEmpty(String str) {
		if(null == str || "".equals(str))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param str 验证字符串
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:05:26
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 判断对象是否为空
	 * @param obj 验证的对象
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:07:00
	 */
	public static boolean isNull(Object obj) {
		return null==obj || "".equals(obj) ? true : false;
	}
	
	/**
	 * 判断对象是否不为空
	 * @param obj 验证的对象
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:07:00
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	/**
	 * 判断集合对象是否为空
	 * @param list 验证的list对象
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:07:49
	 */
	public static boolean isNullList(List list) {
		return null==list || list.size()==0;
	}
	
	/**
	 * 判断集合对象是否不为空
	 * @param list 验证的list对象
	 * @return Boolean
	 * @creator LiBen
	 * @createdate 2015-5-15 下午8:07:49
	 */
	public static boolean isNotNullList(List list) {
		return !isNullList(list);
	}


}
