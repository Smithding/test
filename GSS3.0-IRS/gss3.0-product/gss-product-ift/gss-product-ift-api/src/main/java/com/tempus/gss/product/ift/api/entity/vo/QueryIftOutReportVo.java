package com.tempus.gss.product.ift.api.entity.vo;

public class QueryIftOutReportVo {
    //部门名称
    private String deptName;
    //开始日期
    private String beginDate;
    //截止日期
    private String overDate;
    //查询日期
    private String date;
    //是否改签
    private String changeType;

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getOverDate() {
        return overDate;
    }

    public void setOverDate(String overDate) {
        this.overDate = overDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public QueryIftOutReportVo(String deptName, String beginDate, String overDate, String date, String changeType) {
        this.deptName = deptName;
        this.beginDate = beginDate;
        this.overDate = overDate;
        this.date = date;
        this.changeType = changeType;
    }
}
