package com.huaixuan.network.biz.enums;

public enum EnumFeedbackType {

	LEAVE_WORD("leaveWord","留言"),
    COMPLAINT("complaint","投诉"),
    ASK("ask","询问"),
	AFTER_SERVICE("afterService","售后"),
	WANT_BUY("wantBuy","求购");

    private String code;
    private String name;
    EnumFeedbackType(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }
}
