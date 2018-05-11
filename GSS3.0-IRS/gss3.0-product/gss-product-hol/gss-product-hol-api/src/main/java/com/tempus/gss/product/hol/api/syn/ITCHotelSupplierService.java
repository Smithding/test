package com.tempus.gss.product.hol.api.syn;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.HotelName;
import com.tempus.gss.product.hol.api.entity.LastestResRecord;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.CityAreaScenic;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResInfoList;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.vo.Agent;
/**
 * 同程酒店查询数据库接口
 * @author kai.yang
 *
 */
public interface ITCHotelSupplierService {
	
	/**
	 * 删除当前日期前一天的酒店库存信息
	 * @param strDate 当前日期前一天
	 */
	public void deleteProInfoDetailByDate(String strDate);
	
	/**
	 * 根据酒店ID查询
	 * @param <T>
	 * @param id
	 * @return
	 */
	public ResBaseInfo queryListByResId(Agent agent, Long id);
	
	/**
	 * 根据销售策略id查找
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T queryListByProductUniqueId(Long id, Class<T> clazz);
	
	
	/**
	 * 根据房型id查询
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T queryListByProId(String id, Class<T> clazz);
	
	/**
	 * 查询不为空的所有数据
	 * @return
	 */
	public <T> List<T> queryAllNotNull(Class<T> clazz);
	
	
	/**
	 * 根据日期，城市名，酒店名模糊查询
	 * @param cityName
	 * @param vague
	 * @return
	 */
	public List<ResInfoList> queryResInfoListByVague(String cityName, String vague, String startTime, String endTime);
	/**
	 * 根据城市名查找区域景点
	 * @return
	 */
	public CityAreaScenic queryResInfoListByCity(Agent agent, String cityName) throws GSSException;
	/**
	 * 查询酒店列表
	 * @param agent
	 * @param hotelSearchReq
	 * @return
	 */
	public Future<TCResponse<ResBaseInfo>> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException;
	
	/**
	 * 后台查询酒店列表(结构简单一些)
	 * @param agent
	 * @param hotelSearchReq
	 * @return
	 */
	public TCResponse<ResBaseInfo> queryHotelListForBack(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException;
	
	/**
	 * 根据酒店id和时间查询具体酒店房型信息
	 * @param agent
	 * @param resId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ResBaseInfo queryHotelDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException;
	
	/**
	 * 根据用户id插入最近浏览酒店
	 * @param userId
	 * @return
	 */
	public void insertLastestResByUser(Agent agent, String userId, Long resId) throws GSSException;
	
	/**
	 * 根据用户id查询最近浏览的10个酒店
	 * @param agent
	 * @param userId
	 */
	public List<LastestResRecord> queryLastestResByUser(Agent agent, String userId,int limit) throws GSSException;
	/**
	 * 根据政策ID查询每日价格和总价平均价
	 * @return
	 */
	public ResProBaseInfo queryInventPrice(Agent agent, Long productUniqueId, String startTime, String endTime) throws GSSException;
	/**
	 * 获取城市名模糊匹配列表
	 * @param agent
	 * @param keyword
	 * @param city
	 * @return
	 */
	public List<String> getCityNames(Agent agent,String keyword, String city);
	/**
	 * 可销售状态更新
	 * @param agent
	 * @param resId
	 * @param saleStatus
	 * @return
	 * @throws GSSException
	 */
	public int saleStatusUpdate(Agent agent, Long resId, Integer saleStatus) throws GSSException;
	/**
	 * 查看附近酒店
	 * @param sectionName
	 * @param lat
	 * @param lon
	 * @param distance
	 * @return
	 */
	public List<ResBaseInfo> findNearHotel(Agent agent,String city,String sectionName,double lat,double lon,double distance,int top);
	
	/**
	 * 根据酒店名字模糊匹配下拉信息
	 * @param agent
	 * @param hotelName
	 * @return
	 */
	public List<HotelName> getHotelNames(Agent agent, String hotelName);
	
	/**
	 * 手动添加酒店
	 * @param resId
	 * @return
	 */
	public Boolean insertNewRes(Agent agent, Long resId);
	/**
	 * 添加索引
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public Boolean updateLuceneDate(Agent agent,List<HotelName> list) throws IOException;
	/**
	 * 查询索引
	 * @param agent
	 * @param name
	 * @return
	 */
	public List<HotelName> queryHotelNamesByLucene(Agent agent,String name);
	/**
	 * 修改更新索引
	 * @param resId
	 * @return
	 */
	public Boolean changeLuceneData(HotelName hotelName);
	
}
