/**
 * created since Jun 15, 2009
 */
package com.huaixuan.network.biz.domain.goods;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taobao
 * @version $Id: RuleParseFactory.java,v 0.1 Jun 15, 2009 4:58:06 PM taobao Exp $
 */
public class RuleParseFactory {

    private static Map ruleParseMap;
    public RuleParseFactory(){

    }

    public static void init(){
        if(ruleParseMap==null){
            ruleParseMap=new HashMap();
            ruleParseMap.put("package.scope.id.include", new IdIncludeRuleParse());
            ruleParseMap.put("zoneRule", new ZoneRuleParse());
            ruleParseMap.put("other", new BaseRuleParse());
        }
    }
    public static RuleParse getRuleParse(RuleDef ruleDef){

        if(ruleDef==null)
            return null;
        RuleParseFactory.init();
        if("package.scope.id.include".equalsIgnoreCase(ruleDef.getRuleCode())){
            return  (RuleParse)ruleParseMap.get("package.scope.id.include");
        }else if("give.goods.zone".equalsIgnoreCase(ruleDef.getRuleCode())){
            return  (RuleParse)ruleParseMap.get("zoneRule");
        }else if("price.between".equalsIgnoreCase(ruleDef.getRuleCode())){
            return  (RuleParse)ruleParseMap.get("zoneRule");
        }else if("give.goods.number.between".equalsIgnoreCase(ruleDef.getRuleCode())){
            return  (RuleParse)ruleParseMap.get("zoneRule");
        }
        else{
            return (RuleParse)ruleParseMap.get("other");
        }

    }
}
