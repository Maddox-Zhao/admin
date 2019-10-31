package com.huaixuan.network.biz.domain.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * ��Ʒ����������
 * @author taobao
 * @version $Id: IdIncludeRuleParse.java,v 0.1 Jun 17, 2009 4:08:07 PM taobao Exp $
 */
public class IdIncludeRuleParse extends BaseRuleParse {
    //����ת��Ϊ���ʽ
    @Override
    public  List<Rule> defToRule(Map map, RuleDef ruleDef) {

        if (map == null || ruleDef == null)
            return null;
        Rule rule=new Rule();
        if (map.isEmpty())
            return null;

        //��ʼ�������еĳ�ʼֵ
        rule.setRuleCode(ruleDef.getRuleCode());
        rule.setRuleType(ruleDef.getRuleType());

      //����ת������Ϊ���ù�����Żݹ���ת�� ԭ���Ͻ������⻯�����������Ϊǰ׺�ǲ���ģ���ϵ�ǡ����ߡ���ϵ
        //����Ժ�ת����"����"��ϵ��������������һ��������
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
     * ���ݹ������ֵ��������������������Ӧ��ֵ
     * model="goods.id==:[goods_ids]" value="(goods.id==2)&&(goods.id==3)" ���� map[goods_ids{2,3}]
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
