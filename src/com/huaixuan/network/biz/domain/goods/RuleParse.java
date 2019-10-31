package com.huaixuan.network.biz.domain.goods;

import java.util.List;
import java.util.Map;

public interface RuleParse {
    /**
     * ���ݲ����ѹ����壬ת����ʵ�ʹ���
     * @param map
     * @param ruleDef
     * @return
     */
    public  List<Rule> defToRule(Map map,RuleDef ruleDef);

    /**
     * ���ݹ�����͹������ݣ���������
     * @param rule
     * @param ruleDef
     * @return
     */
    public  Map ruleToParams(List<Rule> rules,RuleDef ruleDef );


}
