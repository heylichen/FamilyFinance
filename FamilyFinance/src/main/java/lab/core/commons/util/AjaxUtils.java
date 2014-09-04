package lab.core.commons.util;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.collection.internal.PersistentBag;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import flexjson.transformer.NullTransformer;

public class AjaxUtils {
	/**
	 * 判断是否为AJAX请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		boolean isAjaxRequest = false;
		String isAjaxStr = request.getHeader("X-Requested-With");
		if (isAjaxStr == null || isAjaxStr.trim().equals("")) {
			isAjaxRequest = false;
		} else {
			isAjaxRequest = true;
		}
		return isAjaxRequest;
	} 
  

	public static String toEagerJSON(Object obj) {
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new NullTransformer(), PersistentBag.class);
		serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss" ), Date.class);
		serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss" ), Timestamp.class);
		
		return serializer.deepSerialize(obj);
	}
}
