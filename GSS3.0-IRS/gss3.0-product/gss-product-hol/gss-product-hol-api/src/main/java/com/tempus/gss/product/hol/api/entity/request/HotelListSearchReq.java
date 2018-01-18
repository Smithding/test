package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店列表搜索入参
 */
public class HotelListSearchReq implements Serializable {
    
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
     * 排序方式: 
     * 格式为前面是需要排序的字段，后面是排序的方式，中间以下划线区分
     */
    @JSONField(name = "OrderBy")
    private List<String> orderBy;
    
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
     * 区县名称
     */
    @JSONField(name = "SectionName")
    private String sectionName;
    
    /**
     * 商圈
     */
    @JSONField(name = "BusinessSectionInfo")
    private String businessSectionInfo;
    /**
     * 景点
     */
    @JSONField(name = "CityScenic")
    private String cityScenic;
    /**
     * 机场火车站
     */
    @JSONField(name = "AirRailWay")
    private String airRailWay;
    /**
     * 地铁
     */
    @JSONField(name = "SubWay")
    private String subWay;
    
    @JSONField(name = "ResId")
    private String resId;
    
    @JSONField(name = "ResGradeId")
    private String resGradeId;
    /**
	 * 酒店是否可售状态，默认为1可售， 0位不可售，此状态与同程无关
	 */
    @JSONField(name = "SaleStatus")
    private Integer saleStatus;
    
    private String bookRes;
    
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

    public List<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<String> orderBy) {
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

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}


	public String getBusinessSectionInfo() {
		return businessSectionInfo;
	}

	public void setBusinessSectionInfo(String businessSectionInfo) {
		this.businessSectionInfo = businessSectionInfo;
	}

	public String getCityScenic() {
		return cityScenic;
	}

	public void setCityScenic(String cityScenic) {
		this.cityScenic = cityScenic;
	}

	public String getAirRailWay() {
		return airRailWay;
	}

	public void setAirRailWay(String airRailWay) {
		this.airRailWay = airRailWay;
	}

	public String getSubWay() {
		return subWay;
	}

	public void setSubWay(String subWay) {
		this.subWay = subWay;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResGradeId() {
		return resGradeId;
	}

	public void setResGradeId(String resGradeId) {
		this.resGradeId = resGradeId;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getBookRes() {
		return bookRes;
	}

	public void setBookRes(String bookRes) {
		this.bookRes = bookRes;
	}
}
