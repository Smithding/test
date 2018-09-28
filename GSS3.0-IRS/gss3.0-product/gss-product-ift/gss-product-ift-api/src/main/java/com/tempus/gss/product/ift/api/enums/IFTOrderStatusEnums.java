package com.tempus.gss.product.ift.api.enums;


public enum IFTOrderStatusEnums {

    PENDING("1","待处理"),

    AUDITED("2","已审核"),

    ISSUING("3","出票中"),

    ISSUED("4","已出票"),

    REFUNDING("5","退票中"),

    WASTING("6","废票中"),

    CHANGING("7","改签中"),

    REFUNDED("8","已退票"),

    WASTED("9","已废票"),

    CHANGED("10","已改签"),

    CANCELLED("11","已取消"),

    REFUSED("14","已拒单");

    private String codeKey;

    private String value;

    IFTOrderStatusEnums(String codeKey,String value){
        this.codeKey = codeKey;
        this.value = value;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static  void main(String[] args){
        System.out.println(IFTOrderStatusEnums.AUDITED.getCodeKey());
    }
}
