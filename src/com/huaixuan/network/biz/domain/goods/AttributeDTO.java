/**
 * created since Mar 12, 2009
 */

package com.huaixuan.network.biz.domain.goods;

import com.hundsun.common.lang.StringUtil;

/**
 * @author taobao
 * @version $Id: AttributeDTO.java,v 0.1 Mar 12, 2009 3:16:25 PM taobao Exp $
 */
public class AttributeDTO extends Attribute {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String getInputHtml(){
         return getInputHtml(0);
    }
    public String getInputHtml(int isShowName){
        StringBuffer strBuff=new StringBuffer();
        strBuff.append("<div>");
        if(isShowName==1){
            if(1==this.getIsNeed()){
                strBuff.append("<span class=\"require-field\">*</span>");
            }
            strBuff.append(this.getAttrName()+":");
        }
        String inputType="";
        if(this.getInputType()!=null)
            inputType=this.getInputType().toLowerCase();
        if("text".equalsIgnoreCase(inputType)){
            strBuff.append("<input type='text' name='"+this.getAttrCode()+"' />");
        }else if("textarea".equals(inputType)){
            strBuff.append("<textarea name='"+this.getAttrCode()+"' /></textarea>");
        }else if("radio".equalsIgnoreCase(inputType)&&getAttrValues()!=null){
            String[] values=this.getAttrValues().split("\n");
            for(int i=0; i<values.length;i++){
                strBuff.append("<input type='radio' name='"+this.getAttrCode()+"' value='"+values[i]+"'/>"+values[i]);
            }
        }
        else if("checkbox".equalsIgnoreCase(inputType)&&getAttrValues()!=null){
            String[] values=this.getAttrValues().split("\n");
            for(int i=0; i<values.length;i++){
                strBuff.append("<input type='checkbox' name='"+this.getAttrCode()+"' value='"+values[i]+"'/>"+values[i]);
            }
        }else if("select".equalsIgnoreCase(inputType)&&getAttrValues()!=null){
            String[] values=this.getAttrValues().split("\n");
            strBuff.append("<select name='"+this.getAttrCode()+"'>");
            for(int i=0; i<values.length;i++){
                strBuff.append("<option value='"+values[i]+"'/>"+values[i]+"</option>");
            }
            strBuff.append("</select>");
        }
        strBuff.append("</div>");
        return strBuff.toString();
   }
   public String getEditHtml(int isShowName,String attrValue){

       String selectValues=getAttrValue(attrValue);
       if(selectValues==null)
           return getInputHtml(isShowName);
       StringBuffer strBuff=new StringBuffer();
       strBuff.append("<div>");
       if(isShowName==1){
           if(1==this.getIsNeed()){
               strBuff.append("<span class=\"require-field\">*</span>");
           }
           strBuff.append(this.getAttrName()+":");
       }
       String inputType=this.getInputType().toLowerCase();
       if("text".equalsIgnoreCase(inputType)){
           strBuff.append("<input type='text' name='"+this.getAttrCode()+"' value='"+selectValues+"'/>");
       }else if("textarea".equals(inputType)){
           strBuff.append("<textarea name='"+this.getAttrCode()+"' />"+selectValues+"</textarea>");
       }else if("radio".equalsIgnoreCase(inputType)&&getAttrValues()!=null){
           String[] values=this.getAttrValues().split("\n");
           for(int i=0; i<values.length;i++){
               if(values[i].trim().equalsIgnoreCase(selectValues)){
                   strBuff.append("<input type='radio' name='"+this.getAttrCode()+"' value='"+values[i]+"' checked/>"+values[i]);
               }else{
                   strBuff.append("<input type='radio' name='"+this.getAttrCode()+"' value='"+values[i]+"'/>"+values[i]);
               }
           }
       }
       else if("checkbox".equalsIgnoreCase(inputType)&&getAttrValues()!=null){
           String[] values=this.getAttrValues().split("\n");
           String[] selectValue=selectValues.split("-");
           boolean flag=false;
           for(int i=0; i<values.length;i++){
               flag=false;
               for(int  j=0;j<selectValue.length;j++){
                   if(selectValue[j].trim().equalsIgnoreCase(values[i].trim())){
                       strBuff.append("<input type='checkbox' name='"+this.getAttrCode()+"' value='"+values[i]+"' checked/>"+values[i]);
                       flag=true;
                       break;
                   }
               }
               if(!flag)
                  strBuff.append("<input type='checkbox' name='"+this.getAttrCode()+"' value='"+values[i]+"'/>"+values[i]);
           }

       }
       else if("select".equalsIgnoreCase(inputType)&&getAttrValues()!=null){
           String[] values=this.getAttrValues().split("\n");
           strBuff.append("<select name='"+this.getAttrCode()+"'>");
           for(int i=0; i<values.length;i++){
               if(values[i].trim().equalsIgnoreCase(selectValues.trim())){
                  strBuff.append("<option value='"+values[i]+"' selected/>"+values[i]+"</option>");
               }else{
                  strBuff.append("<option value='"+values[i]+"'/>"+values[i]+"</option>");
               }
           }
           strBuff.append("</select>");
       }
       strBuff.append("</div>");
       return strBuff.toString();

   }
   private String getAttrValue(String attrValue){

       if(attrValue!=null&&attrValue.trim().length()<=0)
           return null;
       String[] attrs=attrValue.split(";");
       for(int i=0;i<attrs.length;i++){
            String[] temp=attrs[i].split(":");
            if(temp.length<=1)
                return null;
            if(this.getAttrCode().equalsIgnoreCase(temp[0]))
                return temp[1].trim();
       }

       return null;
   }

   private String[] getChoose(String choose){

       if(choose!=null&&choose.trim().length()<=0)
           return null;
       String[] attrs=choose.split("+");
       for(int i=0;i<attrs.length;i++){
            String[] temp=attrs[i].split("*");
            if(temp.length<=1)
                return null;
            if(this.getAttrCode().equalsIgnoreCase(temp[0])){
                return temp;
            }

       }

       return null;
   }

   public String[] getValues(){
	   String atts = StringUtil.replace(this.getAttrValues(), "\r", "");
	   return atts.split("\n");
   }

   public boolean haveValues(){
	   return getAttrValues()!=null;
   }
}
