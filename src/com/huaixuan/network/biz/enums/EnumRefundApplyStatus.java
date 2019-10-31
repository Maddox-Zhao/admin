package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public enum EnumRefundApplyStatus {
    APPLY_NEW("n", "新建"), APPLY_PASS("p", "申请通过"), APPLY_NOT_PASS("u", "申请失败"), APPLY_CANCEL("c",
                                                                                             "取消");
    private String code;
    private String name;

    EnumRefundApplyStatus(String code, String name) {
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
                if ("".equalsIgnoreCase(eOp1)) {
                    ob1Index = 1;
                }
                if ("".equalsIgnoreCase(eOp2)) {
                    ob2Index = 1;
                }
                if ("n".equalsIgnoreCase(eOp1)) {
                    ob1Index = 2;
                }
                if ("n".equalsIgnoreCase(eOp2)) {
                    ob2Index = 2;
                }
                if ("p".equalsIgnoreCase(eOp1)) {
                    ob1Index = 3;
                }
                if ("p".equalsIgnoreCase(eOp2)) {
                    ob2Index = 3;
                }
                if ("u".equalsIgnoreCase(eOp1)) {
                    ob1Index = 4;
                }
                if ("u".equalsIgnoreCase(eOp2)) {
                    ob2Index = 4;
                }

                Integer ob1 = new Integer(ob1Index);
                Integer ob2 = new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(APPLY_NEW.getKey(), APPLY_NEW.getValue());
        enumDataMap.put(APPLY_PASS.getKey(), APPLY_PASS.getValue());
        enumDataMap.put(APPLY_NOT_PASS.getKey(), APPLY_NOT_PASS.getValue());
        enumDataMap.put(APPLY_CANCEL.getKey(), APPLY_CANCEL.getValue());
        return enumDataMap;
    }
}
