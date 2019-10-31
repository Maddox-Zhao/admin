/**
 * created since Mar 12, 2009
 */
package com.huaixuan.network.biz.exception;

/**
 * @author taobao
 * @version $Id: ManagerException.java,v 0.1 Mar 12, 2009 1:57:57 PM taobao Exp $
 */
public class ManagerException extends BaseException {
    private static final long serialVersionUID = -1245376472841943513L;
    /** 序列�*/


    private boolean printable=true;

    /**
     *
     */
    public ManagerException() {
        super();
    }

    /**
     * @param message
     */
    public ManagerException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public ManagerException(Throwable cause) {
        super(cause);
    }

    public ManagerException(Throwable cause,boolean printable){
        super(cause);
        this.printable=printable;
    }

    public ManagerException(boolean printable) {
        super();
        this.printable=printable;
    }

    /**
     * @param message
     */
    public ManagerException(String message,boolean printable) {
        super(message);
        this.printable=printable;
    }

    /**
     * @param message
     * @param cause
     */
    public ManagerException(String message, Throwable cause,boolean printable) {
        super(message, cause);
        this.printable=printable;
    }

    /**
     * @return the printable
     */
    public boolean isPrintable() {
        return printable;
    }

    /**
     * @param printable the printable to set
     */
    public void setPrintable(boolean printable) {
        this.printable = printable;
    }
}
