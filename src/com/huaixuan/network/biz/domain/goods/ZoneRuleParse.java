package com.huaixuan.network.biz.domain.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 区域规则解析器
 * @author taobao
 * @version $Id: ZoneRuleParse.java,v 0.1 Jun 17, 2009 4:07:52 PM taobao Exp $
 */
public class ZoneRuleParse extends BaseRuleParse {
    @Override
    public List<Rule> defToRule(Map map, RuleDef ruleDef) {
        if (map == null || ruleDef == null)
            return null;

        if (map.isEmpty())
            return null;



        String conditionRule = ruleDef.getConditionExpression();
        String promationRule = ruleDef.getResultExpression();

        List condParam=parseRuleToParams(conditionRule);
        List promParam=parseRuleToParams(promationRule);

        List<Rule> list=new ArrayList<Rule>();
        int zoneNum=Integer.parseInt(map.get("zoneNum").toString());
        for(int i=0;i<zoneNum;i++)
        {
             String conditionValue=conditionRule;
             String promationValue=promationRule;
             for(int j=0;j<condParam.size();j++){
                 String param=condParam.get(j).toString();
                 conditionValue=conditionValue.replaceAll(":\\["+param+"\\]", ((List)map.get(param)).get(i).toString());
             }
             for(int j=0;j<promParam.size();j++){
                 String param=promParam.get(j).toString();
                 promationValue=promationValue.replaceAll(":\\["+param+"\\]", ((List)map.get(param)).get(i).toString());
             }
             Rule rule=new Rule();
             rule.setRuleCode(ruleDef.getRuleCode());
             rule.setRuleType(ruleDef.getRuleType());
             rule.setConditionValue(conditionValue);
             rule.setResultValue(promationValue);
             list.add(rule);
        }
        return list;
    }



    public Map ruleToParams(List<Rule> rules,RuleDef ruleDef ){
        Map<String,Object> params=new HashMap<String,Object>();

        String conditionRule = ruleDef.getConditionExpression();
        String promationRule = ruleDef.getResultExpression();
        List condParam=parseRuleToParams(conditionRule);
        List promParam=parseRuleToParams(promationRule);

        Map<String,Object> ruleValueMap=new HashMap<String,Object>();

        for(int i=0;i<condParam.size();i++){
            ruleValueMap.put(condParam.get(i).toString(), new ArrayList());
        }
        for(int i=0;i<promParam.size();i++){
            ruleValueMap.put(promParam.get(i).toString(), new ArrayList());
        }


        for(Rule rule:rules){
            Map curRowCondParam=parseRuleToParams(conditionRule,rule.getConditionValue());
            Map curRowPromParam=parseRuleToParams(promationRule,rule.getResultValue());

            Iterator it = curRowCondParam.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                ((List)ruleValueMap.get(key.toString())).add(value);
            }
            it = curRowPromParam.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                ((List)ruleValueMap.get(key.toString())).add(value);
            }
        }
        params.putAll(ruleValueMap);
        return params;
    }
    public static void main(String[] args) throws Exception {
       String tt="(:[package.scope.all])";
       tt=tt.replaceAll(":\\[package.scope.all\\]", "1");

       System.out.println("tt:"+tt);
    }
}
