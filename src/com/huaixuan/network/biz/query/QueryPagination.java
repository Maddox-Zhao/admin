package com.huaixuan.network.biz.query;

import java.util.List;

/**
 * 
 * @author wangyf
 * @version $Id: QueryPagination.java,v 0.1 2010-7-30 ï¿½ï¿½ï¿½ï¿½01:30:48 wangyf Exp $
 */
public abstract class QueryPagination extends QueryBase {

    private static final long serialVersionUID = 3492300023642822209L;

    /**
     *  ï¿½ï¿½Ò³ï¿½ï¿½ï¿
     */
    private List<?>           items;

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

}
