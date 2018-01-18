package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * TC请求响应集合类
 * @author kai.yang
 *
 * @param <T>
 */
public class TCResponse <T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * 总页数
     */
    @JSONField(name = "TotalPage")
    private Integer totalPatge;
    /**
     * 总条数
     */
    @JSONField(name = "TotalCount")
    private Integer totalCount;
    /**
     * 当前页数
     */
    @JSONField(name = "PageNumber")
    private Integer pageNumber;
    /**
     * 当前页的结果集
     */
    @JSONField(name = "ResponseResult")
    private List<T> responseResult;
    public Integer getTotalPatge() {
        return totalPatge;
    }

    public void setTotalPatge(Integer totalPatge) {
        this.totalPatge = totalPatge;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<T> getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(List<T> responseResult) {
        this.responseResult = responseResult;
    }
	
}
