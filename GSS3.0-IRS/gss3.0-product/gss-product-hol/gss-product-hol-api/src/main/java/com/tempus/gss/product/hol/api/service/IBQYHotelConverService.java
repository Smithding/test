package com.tempus.gss.product.hol.api.service;

import java.util.List;
import java.util.concurrent.Future;

import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;

/**
 * 
 * bqy酒店类型转换
 *
 */
public interface IBQYHotelConverService {

	/**
	 * bqy酒店图片转换tc酒店图片实体
	 * @param hotelImageList
	 * @return
	 */
	List<ImgInfo> convertTCImg(List<ImageList> hotelImageList);
	
	/**
	 * bqy酒店信息转换tc实体
	 * @param hotel
	 * @return
	 */
	ResBaseInfo bqyConvertTcHotelEntity(HotelInfo hotel);
	
	/**
	 *    异步转换酒店类型
	 * @param hotelId
	 * @return
	 */
	Future<ResBaseInfo> asyncConvertTcHotelEntity(Long hotelId);
}
