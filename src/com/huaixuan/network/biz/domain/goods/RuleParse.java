package com.huaixuan.network.biz.domain.goods;

import java.util.List;
import java.util.Map;

public interface RuleParse {
    /**
     * 根据参数把规则定义，转化成实际规则
     * @param map
     * @param ruleDef
     * @return
     */
    public  List<Rule> defToRule(Map map,RuleDef ruleDef);

    /**
     * 根据规则定义和规则数据，解析返回
     * @param rule
     * @param ruleDef
     * @return
     */
    public  Map ruleToParams(List<Rule> rules,RuleDef ruleDef );


}
