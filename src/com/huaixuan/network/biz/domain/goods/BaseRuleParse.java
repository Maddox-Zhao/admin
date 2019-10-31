package com.huaixuan.network.biz.domain.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class BaseRuleParse implements RuleParse {
    protected  final String PLACEHOLDER_PREFIX = ":[";
    protected  final String PLACEHOLDER_SUFFIX = "]";

    //规则转化为表达式

    public List<Rule> defToRule(Map map, RuleDef ruleDef) {

        if (map == null || ruleDef == null)
            return null;
        Rule rule=new Rule();
        if (map.isEmpty())
            return null;


        //初始化规则中的初始值
        rule.setRuleCode(ruleDef.getRuleCode());
        rule.setRuleType(ruleDef.getRuleType());

        //规则转化，分为领用规则和优惠规则转化 原则上替换原则
        String conditionRule = ruleDef.getConditionExpression();
        String promationRule = ruleDef.getResultExpression();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (conditionRule.indexOf(PLACEHOLDER_PREFIX + key+PLACEHOLDER_SUFFIX) > 0) {
                conditionRule=conditionRule.replaceAll(":\\[" + key+"\\]", (String) value);
            }
            int t=promationRule.indexOf(PLACEHOLDER_PREFIX + key+"]");
            if(promationRule!=null&&promationRule.indexOf(PLACEHOLDER_PREFIX + key+PLACEHOLDER_SUFFIX)>=0){
                promationRule=promationRule.replaceAll(":\\[" + key+"\\]", value.toString());
            }
        }

        //设置规则中领用表达式
        rule.setConditionValue(conditionRule);

        //如果规则是优惠规则，还需要设置规则中的优惠表达式
        if(ruleDef.getRuleType()==2){
            if(promationRule!=null&&promationRule.length()>0)
                rule.setResultValue(promationRule);
        }
        List<Rule> list=new ArrayList<Rule>();
        list.add(rule);
        return list;
    }

    /**
     * 从同属于相同的ruleCode的规则数据，反解析说优惠中所带的参数
     * @param rules
     * @param ruleDef
     * @return
     * @see com.hundsun.bible.facade.impl.goods.promation.RuleParse#ruleToParams(java.util.List, com.hundsun.bible.domain.model.RuleDef)
     */
    public Map ruleToParams(List<Rule> rules,RuleDef ruleDef ){
        Map<String,Object> params=new HashMap<String,Object>();
        String conditionExpression=ruleDef.getConditionExpression();
        String promationExpression=ruleDef.getResultExpression();

        //默认基础处理的，只有一个rule;
        Rule rule=(Rule)rules.get(0);
        params.putAll(parseRuleToParams(conditionExpression,rule.getConditionValue()));
        params.putAll(parseRuleToParams(promationExpression,rule.getResultValue()));
        return params;
    }


    /**
     * 根据规则，分析解析出规则定义中有多少参数
     * @param model
     * @return
     */
    protected  List parseRuleToParams(String model){
        List<Object> list=new ArrayList<Object>();
        String modelStr=model;
        while(modelStr.indexOf(":")>=0){
              String preStr=modelStr.substring(0,modelStr.indexOf(PLACEHOLDER_PREFIX));
              String paramName=modelStr.substring(preStr.length()+2,modelStr.indexOf(PLACEHOLDER_SUFFIX));
              modelStr=modelStr.substring(modelStr.indexOf(PLACEHOLDER_SUFFIX)+1);
              list.add(paramName);
        }


        return list;
  }
    /**
     * 根据规则定义和值，反解析出参数和所对应的值
     * model="price>=:[price]" value="price>=4" 返回 map[price,4]
     * @param model
     * @param value
     * @return
     */
    public Map parseRuleToParams(String model,String value){
          Map<String,String> map=new HashMap<String,String>();

          String modelStr=model;
          String valueStr=value;
          while(modelStr.indexOf(":")>=0){
                String preStr=modelStr.substring(0,modelStr.indexOf(PLACEHOLDER_PREFIX));
                String paramName=modelStr.substring(preStr.length()+2,modelStr.indexOf(PLACEHOLDER_SUFFIX));
                modelStr=modelStr.substring(modelStr.indexOf(PLACEHOLDER_SUFFIX)+1);
                String tempStr=modelStr.substring(0,modelStr.indexOf(PLACEHOLDER_PREFIX)<0?0:modelStr.indexOf(PLACEHOLDER_PREFIX));

                int getValueIndex=0;
                if(modelStr.length()==0){
                    getValueIndex=valueStr.length();
                }
                else if(tempStr.length()<=0){
                    getValueIndex=valueStr.indexOf(modelStr,preStr.length());
                    if(getValueIndex==0){
                        getValueIndex=valueStr.length();
                    }
                }else{
                    getValueIndex=valueStr.indexOf(tempStr);
                }
                //如果已经找到下一个点，直接取所有数据
                String paramValue=valueStr.substring(preStr.length(), getValueIndex);
                valueStr=valueStr.substring(valueStr.indexOf(paramValue)+paramValue.length());
                map.put(paramName, paramValue);
          }

          return map;
    }
    public static void main(String[] args) throws Exception {


        String tt="aa:[xxx]";
        tt=tt.replaceAll(":\\[xxx\\]", "bbb");
        System.out.println(tt);
        BaseRuleParse parse=new BaseRuleParse();

        Map map=parse.parseRuleToParams("(price >=:[lowerPrice]) && (price <:[topPrice])", "(price >=200) && (price <400)");

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key:"+key+" value:"+value);
        }

    }
}
