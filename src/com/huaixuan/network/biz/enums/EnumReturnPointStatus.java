package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 返点规则状态
 * @author fanyj
 *
 */
public enum EnumReturnPointStatus {
    ABLE("y", "有效"), DISABLE("n", "无效"), RELATED("a", "已关联");

    private String code;
    private String name;

    EnumReturnPointStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new TreeMap<String, String>(new Comparator() {
            public int compare(Object element1, Object element2) {
                String eOp1 = (String) element1;
                String eOp2 = (String) element2;
                if (eOp1 == null) {
                    return -1;
                }
                if (eOp2 == null) {
                    return 1;
                }

                int ob1Index = 0;
                int ob2Index = 0;
                if ("y".equalsIgnoreCase(eOp1)) {
                    ob1Index = 1;
                }
                if ("y".equalsIgnoreCase(eOp2)) {
                    ob2Index = 1;
                }
                if ("n".equalsIgnoreCase(eOp1)) {
                    ob1Index = 2;
                }
                if ("n".equalsIgnoreCase(eOp2)) {
                    ob2Index = 2;
                }
                if ("a".equalsIgnoreCase(eOp1)) {
                    ob1Index = 3;
                }
                if ("a".equalsIgnoreCase(eOp2)) {
                    ob2Index = 3;
                }

                Integer ob1 = new Integer(ob1Index);
                Integer ob2 = new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(ABLE.getKey(), ABLE.getValue());
        enumDataMap.put(DISABLE.getKey(), DISABLE.getValue());
        enumDataMap.put(RELATED.getKey(), RELATED.getValue());
        return enumDataMap;
    }

}
