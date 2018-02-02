package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.vo.Agent;

/**
 * 酒店搜索入参
 * Created by luofengjie on 2017/3/24.
 */
public class HotelSearchReq extends BaseRequest {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 城市编号
     */
    @JSONField(name = "CityCode")
    private String cityCode;
    /**
     * 入住日期(yyyy-MM-dd)
     */
    @JSONField(name = "Begin")
    private String begin;
    /**
     * 离店日期(yyyy-MM-dd)
     */
    @JSONField(name = "End")
    private String end;
    /**
     * 关键词
     */
    @JSONField(name = "Keyword")
    private String keyword;
    /**
     * 排序方式(101是按最低价格逆序排序，102按最低价格顺序排序，此外默认排序)
     */
    @JSONField(name = "OrderBy")
    private String orderBy;
    /**
     * 页数
     */
    @JSONField(name = "PageCount")
    private Integer pageCount;
    /**
     * 每页行数
     */
    @JSONField(name = "RowCount")
    private Integer rowCount;
    /**
     * 过滤条件
     */
    @JSONField(name = "Condition")
    private SearchCondition searchCondition;
    /**
     * 当前用户信息
     */
    private Agent agent;

    public SearchCondition getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(SearchCondition searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
