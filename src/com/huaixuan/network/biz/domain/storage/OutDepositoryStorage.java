package com.huaixuan.network.biz.domain.storage;

public class OutDepositoryStorage extends Storage {

    private static final long serialVersionUID = -1475831132712862762L;

    private String            supplierName;
    private String            locName;
    private String            oriCount;
    private String            depName;
    private String            depType;
    private String            amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getOriCount() {
        return oriCount;
    }

    public void setOriCount(String oriCount) {
        this.oriCount = oriCount;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    /**
     * @return the depType
     */
    public String getDepType() {
        return depType;
    }

    /**
     * @param depType the depType to set
     */
    public void setDepType(String depType) {
        this.depType = depType;
    }

}
