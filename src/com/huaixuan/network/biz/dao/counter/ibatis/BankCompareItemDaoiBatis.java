package com.huaixuan.network.biz.dao.counter.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.BankCompareItemDao;
import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/** 
 * @version 3.2.0 
 */
@Service("bankCompareItemDao")
public class BankCompareItemDaoiBatis implements BankCompareItemDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    protected final Log          log = LogFactory.getLog(this.getClass());

    /**
     * 
     * ɾ�����ж����ļ���ǰ������
     */
    public void delBeforeFiveDaysBankCompareItem() {

        this.sqlMapClientTemplate.delete("delBeforeFiveDaysBankCompareItem");
    }

    /**
     * ������Ӷ����ļ�������ϸ�������Ӧ��listu
     * @param listu
     * @return
     */
    public void addBeankCompareItemList(List listu) {
        this.executeBatchOperation("addBankCompareItem", listu, "insert");
    }

    private int executeBatchOperation(final String statementName, final List parameterList,
                                      final String flag) {
        Long exectuteSucValue = null;
        exectuteSucValue = (Long) sqlMapClientTemplate.execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                Long states = Long.valueOf(0);
                try {
                    executor.startBatch();
                    for (int i = 0; i < parameterList.size(); i++) {
                        if (flag.equals("update")) {
                            executor.update(statementName, parameterList.get(i));
                        } else if (flag.equals("insert")) {
                            executor.insert(statementName, parameterList.get(i));
                        } else if (flag.equals("delete")) {
                            executor.delete(statementName, parameterList.get(i));
                        }
                    }

                    executor.executeBatch();
                } catch (Exception e) {
                    states = Long.valueOf(-1);
                    log.error(e);
                }
                return states;

            }
        });
        if (exectuteSucValue.intValue() == -1) {
            throw new RuntimeException();
        }
        return parameterList.size();
    }

    /**
     * ������ж����ļ�������ϸ
     * @param bankCompareItemu
     */
    public void addBankCompareItem(BankCompareItem bankCompareItemu) {
        this.sqlMapClientTemplate.insert("addBankCompareItem", bankCompareItemu);
    }

    /**
     * �༭���ж����ļ�������ϸ
     * @param bankCompareItemu
     */
    public void editBankCompareItem(BankCompareItem bankCompareItemu) {
        this.sqlMapClientTemplate.update("editBankCompareItem", bankCompareItemu);
    }

    /**
     * ����idɾ����Ӧ��ϸ
     * @param id
     */
    public void removeBankCompareItem(Long id) {
        this.sqlMapClientTemplate.delete("removeBankCompareItem", id);
    }

    /**
     *����id�õ���Ӧ��ϸ 
     * @param id
     * @return
     */
    public BankCompareItem getBankCompareItem(Long id) {
        return (BankCompareItem) this.sqlMapClientTemplate.queryForObject("getBankCompareItem", id);
    }

    /**
     * �õ���ϸlist
     * @return
     */
    public List<BankCompareItem> getBankCompareItems() {
        return this.sqlMapClientTemplate.queryForList("getBankCompareItems", null);
    }

}
