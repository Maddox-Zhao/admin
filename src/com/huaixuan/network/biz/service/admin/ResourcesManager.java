package com.huaixuan.network.biz.service.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Resources;

public interface ResourcesManager  {

    /**
     * 新增resources
     * @param resources
     */
    public void addResources(Resources resources);

    /**
     * 编辑resources
     * @param resources
     */
    public void editResources(Resources resources);

    /**
     * 删除resources
     * @param resourcesId
     */
    public void removeResources(Long resourcesId);

    /**
     * 根据id获得resource
     * @param resourcesId
     * @return
     */
    public Resources getResources(Long resourcesId);

    /**
     * 获得全部resources
     * @return
     */
    public List<Resources> getResourcess();

    /**
     * 根据map获得resource
     * @param resourcesId
     * @return
     */
    public List<Resources> getResourcesByMap(Map map);

    /**根据数值区间取得对应的值
     * @param type类型
     * @param point数值
     * @return
     */
    String getResorcesValue(String type, Long point);

    /**根据类型，名称取得对应的值
     * @param type
     * @param name
     * @return
     */
    String getResorcesValue(String type, String name);

    /**
     * 根据type,name获得resource
     * @param type
     * @param name
     * @return
     */
    Resources getResourcesByTypeAndName(String type, String name);

    /**
     * 根据type获得resources
     * @param type
     * @return
     */
    List<Resources> getResourcesByType(String type);
    
    

}
