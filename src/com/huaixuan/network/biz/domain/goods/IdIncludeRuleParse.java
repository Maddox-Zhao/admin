package com.huaixuan.network.biz.domain.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 商品包括解析器
 * @author taobao
 * @version $Id: IdIncludeRuleParse.java,v 0.1 Jun 17, 2009 4:08:07 PM taobao Exp $
 */
public class IdIncludeRuleParse extends BaseRuleParse {
    //规则转化为表达式
    @Override
    public  List<Rule> defToRule(Map map, RuleDef ruleDef) {

        if (map == null || ruleDef == null)
            return null;
        Rule rule=new Rule();
        if (map.isEmpty())
            return null;

        //初始化规则中的初始值
        rule.setRuleCode(ruleDef.getRuleCode());
        rule.setRuleType(ruleDef.getRuleType());

      //规则转化，分为领用规则和优惠规则转化 原则上进行特殊化处理，这里的认为前缀是不变的，关系是”或者“关系
        //如果以后转化的"并且"关系，必须重新重做一个解析器
        String conditionRule = ruleDef.getConditionExpression();
        List goodsIds=(List)map.get("goodsIds");
        StringBuffer  expression=new StringBuffer();
        if(conditionRule.indexOf(":[goodsIds]")>0){
           // conditionRule.replaceAll(":{goodsIds}", "");
            conditionRule=conditionRule.substring(0, conditionRule.indexOf(":[goodsIds]"));
             for(int i=0;i<goodsIds.size();i++){
                 if(i!=0){
                     expression.append("||");
                 }
                 expression.append("("+conditionRule+goodsIds.get(i)+")");
             }
        }

        rule.setConditionValue(expression.toString());

        List<Rule> list=new ArrayList<Rule>();
        list.add(rule);
        return list;
    }

    @Override
    public Map ruleToParams(List<Rule> rules,RuleDef ruleDef ){
        Map<String,Object> params=new HashMap<String,Object>();
        String conditionRule = ruleDef.getConditionExpression();
        Rule rule=(Rule)rules.get(0);
        params.putAll(parseRuleToParams(conditionRule,rule.getConditionValue()));
        return params;
    }


    /**
     * 根据规则定义和值，反解析出参数和所对应的值
     * model="goods.id==:[goods_ids]" value="(goods.id==2)&&(goods.id==3)" 返回 map[goods_ids{2,3}]
     * @param model
     * @param value
     * @return
     * @see com.hundsun.bible.facade.impl.goods.promation.BaseRuleParse#parseRuleToParams(java.lang.String, java.lang.String)
     */
    public Map parseRuleToParams(String model,String value){
        Map<String,Object> map=new HashMap<String,Object>();

        String modelStr=model;
        String valueStr=value;

        String preStr=model.substring(0,model.indexOf(":["));
        String paramName=model.substring(preStr.length()+2,model.indexOf("]"));

        List list=new java.util.ArrayList();

        while(valueStr.indexOf(preStr)>=0){
              int curIndex=valueStr.indexOf(preStr);
              valueStr=valueStr.substring(curIndex);
              String paramValue=valueStr.substring(preStr.length(), valueStr.indexOf(")"));
              valueStr=valueStr.substring(preStr.length()+1);
              list.add(paramValue);
        }

        map.put(paramName, list);
        return map;
    }
    public static void main(String[] args) throws Exception {

        IdIncludeRuleParse parse=new IdIncludeRuleParse();

        Map map=parse.parseRuleToParams("goods.id==:{goods_id}", "(goods.id==2)&&(goods.id==3)");

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key:"+key+" value:"+value);
        }

    }
}
