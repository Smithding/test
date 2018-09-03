package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;

public class UnpItemTypeReportVo implements Serializable {
    private String eName;//英文文名称
    private String value;//value值
    private String type;//类型
    private String cName;//中文名称

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
