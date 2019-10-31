package com.huaixuan.network.common.util.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.api.convert.reader.ConvertReader;
import com.taobao.api.convert.reader.ConvertUtil;
import com.taobao.api.json.JSONArray;
import com.taobao.api.json.JSONException;
import com.taobao.api.json.JSONObject;

public class MyTaobaoJsonCovertUtil {
    /**
     * ��JSONת����Ϊ�� total_results �� JSONArray �� Map ��JSONArrayʹ���Ա����ŵ�API������ת������ض�������
     * @param in �Ա��ӿڷ��ص���Ϣ(��������Ϊ����) ��{"trades_sold_get_response":{"trades":{"trade":[{"buyer_nick":"lang7"}]},"total_results":1}}
     * @param arrayname �����Ķ����� �磺Trade
     * @param methodName ���õĽӿ��� �磺taobao.trades.sold.get
     * @return null������ָ����arrayname��������
     */
    public static Map<String,Object> json2ResultListMap(String in,String arrayname, String methodName) {
        try {
            Map<String,Object> resultListMap = new HashMap<String,Object>();
            JSONObject json = new JSONObject(in);
            String rightRootName = ConvertUtil.toResponseRootNameV2(methodName);
            if (json.has(rightRootName)) {
                if (json.get(rightRootName) == JSONObject.NULL) {
                    return null;
                }
                //���һ��
                json = json.getJSONObject(ConvertUtil.toResponseRootNameV2(methodName));
                if(arrayname==null){
                    return null;
                }else{
                    //���������а�������������
                    int total_results = json.getInt("total_results");
                    resultListMap.put("total_results", total_results);
                    if(total_results>0){
                        //�����Ķ����JSONArray
                        resultListMap.put("JSONArray", json.getJSONObject(arrayname.toLowerCase()+"s").getJSONArray(arrayname.toLowerCase()));
                    }else{
                        resultListMap.put("JSONArray", new JSONArray());
                    }
                    return resultListMap;
                }

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T json2Modle(String in, Class<T> rspClass, String methodName) {
        try {
            JSONObject json = new JSONObject(in);
            String rightRootName = ConvertUtil.toResponseRootNameV2(methodName);
            if (json.has(rightRootName)) {
                if (json.get(rightRootName) == JSONObject.NULL) {
                    return null;
                }
                json = json.getJSONObject(ConvertUtil.toResponseRootNameV2(methodName));
                //System.out.println("rspClass.getName()");
                if(json.has(rspClass.getSimpleName().toLowerCase())){
                    json = json.getJSONObject(rspClass.getSimpleName().toLowerCase());
                }else{
                    return null;
                }
            } else {
                //json = json.getJSONObject("error_response");
                return null;
            }

            return getModelFromJson(json, rspClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getModelFromJson(final JSONObject json, Class<T> rspClass) {
        if (json == null)
            return null;

        return ConvertUtil.getModel(rspClass, new ConvertReader() {
            public List<?> getModels(String name, String subName, Class<?> subType) {
                try {
                    if (subName == null || "".equals(subName.trim()))
                        subName = ConvertUtil.hump2UnderLine(subType.getSimpleName());

                    JSONArray subJsonArray = json.getJSONObject(name).getJSONArray(subName);
                    List models = null;

                    for (int i = 0; i < subJsonArray.length(); i++) {
                        Object tmpObj = subJsonArray.get(i);
                        if (tmpObj instanceof JSONObject) {
                            tmpObj = getModelFromJson(subJsonArray.getJSONObject(i), subType);
                        }
                        if (tmpObj != null) {
                            if (models == null) models = new ArrayList();
                            models.add(tmpObj);
                        }
                    }

                    return models;
                } catch (JSONException e) {
                    // forget this
                }
                return null;
            }

            public Object getSimpleModel(String name, Class<?> type) {
                try {
                    JSONObject subJson = json.getJSONObject(name);
                    return getModelFromJson(subJson, type);
                } catch (JSONException e) {
                    // forget this
                }
                return null;
            }

            public String getValueAsString(String name) {
                try {
                    return json.getString(name);
                } catch (JSONException e) {
                    // forget this
                }
                return null;
            }
        });
    }

}
