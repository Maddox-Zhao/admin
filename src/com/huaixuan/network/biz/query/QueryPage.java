package com.huaixuan.network.biz.query;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class QueryPage extends QueryBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1092399356406029960L;
	Map<String, String> parameterMap = new java.util.HashMap<String, String>();

	Object obj;
	List<?> items;
	private int total=0;
	@Override
	public Map<String, String> getParameters() {
		Class clazz = obj.getClass();
		HashMap<String, String> resMap = new HashMap<String, String>();
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			getClass(clazz, map, obj);
			resMap = convertHashMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
	
	public Map<String, Object> getParametersQuery() {
		Class clazz = obj.getClass();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		try {
			getClass(clazz, resMap, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	public QueryPage(Object obj) {
		this.obj = obj;
	}

	@SuppressWarnings("unchecked")
	private static void getClass(Class clazz, HashMap map, Object obj) throws Exception {
		if (clazz.getSimpleName().equals("Object")) {
			return;
		}

		if (obj instanceof Map) {
			Map<String, Object> ori = (Map<String, Object>) obj;
			for (Entry<String, Object> en : ori.entrySet()) {
				Object v = en.getValue();
				if (v instanceof Number || v instanceof String || v instanceof Character || v instanceof java.sql.Date
						|| v instanceof java.util.Date || v instanceof Timestamp) {
					map.put(en.getKey(), v);
				}
			}
		} else {
			java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					String name = fields[i].getName();
					if ("serialVersionUID".equals(name)) {
						continue;
					}
					Object v = fields[i].get(obj);

					if (v instanceof Number || v instanceof String || v instanceof Character
							|| v instanceof java.sql.Date || v instanceof java.util.Date || v instanceof Timestamp) {
						map.put(name, v);
					}

				}
			}
			Class superClzz = clazz.getSuperclass();
			getClass(superClzz, map, obj);
		}
	}

	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, String> convertHashMap(HashMap map) throws Exception {

		HashMap<String, String> newMap = new HashMap<String, String>();
		Set keys = map.keySet();
		java.util.Iterator it = keys.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			convertToString(map.get(key), newMap, key);
		}

		return newMap;
	}

	/**
	 * 
	 * @param value
	 * @param newMap
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	private static void convertToString(Object value, HashMap newMap, Object key) {
		if (value != null) {
			Class clazz = value.getClass();
			if (isBaseType(clazz)) {
				newMap.put(key, value.toString());
			} else if (clazz == String.class) {
				newMap.put(key, value.toString());
			} else if (clazz == Date.class) {
				Date date = (Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				newMap.put(key, sdf.format(date));
			} else if (clazz == Timestamp.class) {
				Timestamp timestamp = (Timestamp) value;
				long times = timestamp.getTime();
				Date date = new Date(times);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				newMap.put(key, sdf.format(date));
			} else if (clazz == java.sql.Date.class) {
				java.sql.Date sqlDate = (java.sql.Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				newMap.put(key, sdf.format(sqlDate));
			} else {
				newMap.put(key, value);
			}
		}

	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static boolean isBaseType(Class clazz) {

		if (clazz == Integer.class) {
			return true;
		}
		if (clazz == Long.class) {
			return true;
		}
		if (clazz == Double.class) {
			return true;
		}
		if (clazz == Byte.class) {
			return true;
		}
		if (clazz == Float.class) {
			return true;
		}
		if (clazz == Short.class) {
			return true;
		}
		if (clazz == Boolean.class) {
			return true;
		}
		return false;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
