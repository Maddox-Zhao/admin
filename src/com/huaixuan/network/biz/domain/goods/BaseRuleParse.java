package com.huaixuan.network.biz.domain.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class BaseRuleParse implements RuleParse {
    protected  final String PLACEHOLDER_PREFIX = ":[";
    protected  final String PLACEHOLDER_SUFFIX = "]";

    //����ת��Ϊ���ʽ

    public List<Rule> defToRule(Map map, RuleDef ruleDef) {

        if (map == null || ruleDef == null)
            return null;
        Rule rule=new Rule();
        if (map.isEmpty())
            return null;


        //��ʼ�������еĳ�ʼֵ
        rule.setRuleCode(ruleDef.getRuleCode());
        rule.setRuleType(ruleDef.getRuleType());

        //����ת������Ϊ���ù�����Żݹ���ת�� ԭ�����滻ԭ��
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

        //���ù��������ñ��ʽ
        rule.setConditionValue(conditionRule);

        //����������Żݹ��򣬻���Ҫ���ù����е��Żݱ��ʽ
        if(ruleDef.getRuleType()==2){
            if(promationRule!=null&&promationRule.length()>0)
                rule.setResultValue(promationRule);
        }
        List<Rule> list=new ArrayList<Rule>();
        list.add(rule);
        return list;
    }

    /**
     * ��ͬ������ͬ��ruleCode�Ĺ������ݣ�������˵�Ż��������Ĳ���
     * @param rules
     * @param ruleDef
     * @return
     * @see com.hundsun.bible.facade.impl.goods.promation.RuleParse#ruleToParams(java.util.List, com.hundsun.bible.domain.model.RuleDef)
     */
    public Map ruleToParams(List<Rule> rules,RuleDef ruleDef ){
        Map<String,Object> params=new HashMap<String,Object>();
        String conditionExpression=ruleDef.getConditionExpression();
        String promationExpression=ruleDef.getResultExpression();

        //Ĭ�ϻ�������ģ�ֻ��һ��rule;
        Rule rule=(Rule)rules.get(0);
        params.putAll(parseRuleToParams(conditionExpression,rule.getConditionValue()));
        params.putAll(parseRuleToParams(promationExpression,rule.getResultValue()));
        return params;
    }


    /**
     * ���ݹ��򣬷������������������ж��ٲ���
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
     * ���ݹ������ֵ��������������������Ӧ��ֵ
     * model="price>=:[price]" value="price>=4" ���� map[price,4]
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
                //����Ѿ��ҵ���һ���㣬ֱ��ȡ��������
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
