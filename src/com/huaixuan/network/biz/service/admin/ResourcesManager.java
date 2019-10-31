package com.huaixuan.network.biz.service.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Resources;

public interface ResourcesManager  {

    /**
     * ����resources
     * @param resources
     */
    public void addResources(Resources resources);

    /**
     * �༭resources
     * @param resources
     */
    public void editResources(Resources resources);

    /**
     * ɾ��resources
     * @param resourcesId
     */
    public void removeResources(Long resourcesId);

    /**
     * ����id���resource
     * @param resourcesId
     * @return
     */
    public Resources getResources(Long resourcesId);

    /**
     * ���ȫ��resources
     * @return
     */
    public List<Resources> getResourcess();

    /**
     * ����map���resource
     * @param resourcesId
     * @return
     */
    public List<Resources> getResourcesByMap(Map map);

    /**������ֵ����ȡ�ö�Ӧ��ֵ
     * @param type����
     * @param point��ֵ
     * @return
     */
    String getResorcesValue(String type, Long point);

    /**�������ͣ�����ȡ�ö�Ӧ��ֵ
     * @param type
     * @param name
     * @return
     */
    String getResorcesValue(String type, String name);

    /**
     * ����type,name���resource
     * @param type
     * @param name
     * @return
     */
    Resources getResourcesByTypeAndName(String type, String name);

    /**
     * ����type���resources
     * @param type
     * @return
     */
    List<Resources> getResourcesByType(String type);
    
    

}
