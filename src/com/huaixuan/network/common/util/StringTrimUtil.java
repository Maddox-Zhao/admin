package com.huaixuan.network.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 提供对字符串的一些操作
 * @author lizhensi
 * @createtime 2010-12-22 上午10:15:47
 *
 */
public class StringTrimUtil {

    /**
     * 对pojo对象所有{@code java.lang.String}类型属性去除前后空格（该属性必须同时拥有getter和setter才能被执行此操作）
     * @param pojo
     * @author lizhensi
     * @createtime 2010-12-23 上午10:21:08
     */
    public static void trim(Object pojo) {
        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType() == String.class) {
                String fName = f.getName();
                String pojoGetSet = fName.substring(0, 1).toUpperCase()
                                    + fName.substring(1, fName.length());
                try {
                    Method getter = pojo.getClass().getMethod("get" + pojoGetSet);
                    Method setter = pojo.getClass().getMethod("set" + pojoGetSet, String.class);

                    Object value = getter.invoke(pojo);
                    if (value != null) {
                        setter.invoke(pojo, com.hundsun.network.melody.common.util.StringUtil
                            .trim(value.toString()));
                    }
                } catch (SecurityException e) {
                } catch (NoSuchMethodException e) {
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }

    }

    /**
     * 对pojo对象所有{@code java.lang.String}类型属性去除前后空格（该属性必须同时拥有getter和setter才能被执行此操作）
     * @param pojo
     * @param exceptFields 不执行该操作的属性名
     * @author lizhensi
     * @createtime 2010-12-23 上午10:24:24
     */
    public static void trim(Object pojo, String[] exceptFields) {
        Set<String> exceptFieldSet = new HashSet<String>(exceptFields.length);
        for (String f : exceptFields) {
            exceptFieldSet.add(f);
        }

        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType() == String.class) {
                String fName = f.getName();
                if (exceptFieldSet.contains(fName)) {
                    continue;
                }
                String pojoGetSet = fName.substring(0, 1).toUpperCase()
                                    + fName.substring(1, fName.length());
                try {
                    Method getter = pojo.getClass().getMethod("get" + pojoGetSet);
                    Method setter = pojo.getClass().getMethod("set" + pojoGetSet, String.class);

                    Object value = getter.invoke(pojo);
                    if (value != null) {
                        setter.invoke(pojo, com.hundsun.network.melody.common.util.StringUtil
                            .trim(value.toString()));
                    }
                } catch (SecurityException e) {
                } catch (NoSuchMethodException e) {
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }
    }
}
