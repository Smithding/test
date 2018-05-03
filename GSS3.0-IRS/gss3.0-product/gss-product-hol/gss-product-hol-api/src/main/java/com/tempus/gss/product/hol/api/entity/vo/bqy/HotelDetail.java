package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.List;

/**
 *酒店详细信息
 */
public class HotelDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;					//主键ID,等同于酒店ID

	private String province;     		//省份编号
	
	private String provinceName;     	//省份名称
	
	private String city;				//城市编号
	
	private String cityName;			//城市名称
	
	private String county;				//县区编号
	
	private String countyName;			//县区名称
	
	private String hotelName;			//酒店名称
	
	private String hotelNameEN;			//酒店英文名称
	
	private HotelEntity hotelEntity;	//酒店详细信息
	
	private HotelInfo hotelInfo;		//酒店详细信息
	
	private int commentCount;			//酒店评论数
	
	private List<HotelRoomFacility> hotelRoomFacility; //酒店房间设施
	
	private List<ImageList> hotelImageList; //酒店图片集合
	
	private List<RoomImageList> roomImageList; //房型图片集合
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public HotelEntity getHotelEntity() {
		return hotelEntity;
	}

	public void setHotelEntity(HotelEntity hotelEntity) {
		this.hotelEntity = hotelEntity;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public List<ImageList> getHotelImageList() {
		return hotelImageList;
	}

	public void setHotelImageList(List<ImageList> hotelImageList) {
		this.hotelImageList = hotelImageList;
	}

	public List<RoomImageList> getRoomImageList() {
		return roomImageList;
	}

	public void setRoomImageList(List<RoomImageList> roomImageList) {
		this.roomImageList = roomImageList;
	}

	public List<HotelRoomFacility> getHotelRoomFacility() {
		return hotelRoomFacility;
	}

	public void setHotelRoomFacility(List<HotelRoomFacility> hotelRoomFacility) {
		this.hotelRoomFacility = hotelRoomFacility;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelNameEN() {
		return hotelNameEN;
	}

	public void setHotelNameEN(String hotelNameEN) {
		this.hotelNameEN = hotelNameEN;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
	
}
