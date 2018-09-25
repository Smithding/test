package com.tempus.gss.product.ift.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.ops.util.SortUtils;
import com.tempus.gss.product.ift.api.entity.policy.AirLineData;
import com.tempus.gss.product.ift.api.entity.policy.Areas;
import com.tempus.gss.product.ift.api.entity.policy.Capital;
import com.tempus.gss.product.ift.api.entity.policy.IftContinent;
import com.tempus.gss.product.ift.api.entity.policy.PolicyCity;
import com.tempus.gss.product.ift.api.entity.policy.States;
import com.tempus.gss.product.ift.api.service.NewIftPolicyDataService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.tbd.entity.Airline;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.entity.AreaInfo;
import com.tempus.tbd.entity.Continent;
import com.tempus.tbd.entity.Country;
import com.tempus.tbd.entity.Province;
import com.tempus.tbd.service.IAirlineService;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbd.service.IAreaInfoService;
import com.tempus.tbd.service.IContinentService;
import com.tempus.tbd.service.ICountryService;
import com.tempus.tbd.service.IProvinceService;

@Service
public class NewIftPolicyDataServiceImpl implements NewIftPolicyDataService {
	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(NewIftPolicyDataServiceImpl.class);
	/** 订单数量缓存过期时间（秒） */
	protected long expireTime = 60;

	/** 订单数量保存到缓存中过期时间的单位, 默认: SECONDS */
	protected static final TimeUnit TIME_UNIT = TimeUnit.DAYS;
	/** 政策城市控件数据redis数据KEY */
	protected static final String AIRLINEDATE_JSON = "airLineDateJson";
	protected static final String CHINA_KEY = "CN0";
	protected static final String CHINA_LABLE = "中国境内";
	protected static final String FOREIGN_KEY = "CN1";
	protected static final String FOREIGN_LABLE = "境外";
	protected static final String GLOBAL_KEY = "A00";
	protected static final String GLOBAL_LABLE = "全球";
	
	// 需要特殊处理的城市
	public final static String CITY_CODE_PEK = "PEK";
	public final static String CITY_NAME_PEK = "北京首都";
	public final static String CITY_CODE_NAY = "NAY";
	public final static String CITY_NAME_NAY = "北京南苑";
	public final static String CITY_CODE_SHA = "SHA";
	public final static String CITY_NAME_SHA = "上海虹桥";
	public final static String CITY_CODE_PVG = "PVG";
	public final static String CITY_NAME_PVG = "上海浦东";
	
	@Reference
	private IContinentService continentService;
	@Reference
	private IAreaInfoService areaInfoService;
	@Reference
	private IAirportService airportService;
	@Reference
	private ICountryService countryService;
	@Reference
	private IProvinceService provinceService;
	@Reference
	private IAirlineService airlineService;
	@Resource(name = "redisTemplate")
	protected ValueOperations<String, String> cache;

	@Override
	public String getAirLineData() {
		String resultJson = cache.get(AIRLINEDATE_JSON);
		return resultJson;
	}

	@Override
	public String setAirLineData() {
		// TODO Auto-generated method stub
		List<AirLineData> airLineDatas = new ArrayList<AirLineData>();
		PolicyCity city = new PolicyCity();
		try {
			// 境内
			AirLineData chinaDate = new AirLineData();
			chinaDate.setKey(CHINA_KEY);
			chinaDate.setLabel(CHINA_LABLE);
			// 境外
			AirLineData foreignDate = new AirLineData();
			foreignDate.setKey(FOREIGN_KEY);
			foreignDate.setLabel(FOREIGN_LABLE);
			// 全球
			AirLineData globalData = new AirLineData();
			globalData.setKey(GLOBAL_KEY);
			globalData.setLabel(GLOBAL_LABLE);
			List<States> airLineChina = this.getAirLineChina();// 国内政策城市控件
			List<States> airLineGlobal = this.getAirLineGlobal();// 境外和全球政策城市控件
			foreignDate.setData(airLineGlobal);// 境外添加对应数据结果
			globalData.setData(airLineGlobal);// 全球添加对应数据结果
			chinaDate.setData(airLineChina);// 国内
			airLineDatas.add(chinaDate);// 中国境内
			airLineDatas.add(foreignDate);// 境外
			airLineDatas.add(globalData);// 全球
			city.setAirLineData(airLineDatas);

			String json = JsonUtil.toJson(city); // 加入redis缓存
			cache.set(AIRLINEDATE_JSON, json);
			cache.getOperations().expire(AIRLINEDATE_JSON, expireTime, TIME_UNIT);
		} catch (Exception e) {
			logger.error("setAirLineData加入缓存异常" + e.getMessage());
			return "fail";
		}
		String json = JsonUtil.toJson(city);
		return json;
	}

	/**
	 * 国内政策城市控件
	 * 
	 * @return
	 */
	private List<States> getAirLineChina() {
		List<Province> provincesList = provinceService.queryAllProvinces(null);
		// 封装州的数据集合
		List<States> listStates = new ArrayList<States>();
		States states = new States();
		states.setKey(null);
		states.setLabel("全国");
		listStates.add(states);
		// 封装所有的区数据
		List<Areas> listAreas = new ArrayList<Areas>();
		for (Province province : provincesList) {
			List<Airport> airportList = airportService.queryAirportByProvince(province.getProvinceEName());
			// 获取所有的区实体类
			Areas areas = new Areas();
			areas.setKey(province.getProvinceCName());
			areas.setLabel(province.getProvinceEName());
			// 封装国家数据
			List<IftContinent> listContinent = new ArrayList<IftContinent>();
			for (Airport airport : airportList) {
				// 封装国家数据
				IftContinent con = new IftContinent();
				con.setKey(airport.getAirportCode());
				con.setLabel(airport.getAirportCName());
				listContinent.add(con);
			}
			listAreas.add(areas);
			areas.setData(listContinent);
		}
		states.setData(listAreas);
		return listStates;
	}

	/**
	 * 境外和全球政策城市控件
	 * 
	 * @return
	 */
	public List<States> getAirLineGlobal() {
		
		// 封装州的数据集合
		List<States> listStates = new ArrayList<States>();
		
		// 获取所有的州
		List<AreaInfo> areaInfos = areaInfoService.queryAllAreas();
		for (AreaInfo areaInfo : areaInfos) {
			// 封装州的数据实体类
			States states = new States();
			states.setKey(areaInfo.getAreaCode().replaceAll(" ",""));
			states.setLabel(areaInfo.getAreaName());
			listStates.add(states);
			// 获取所有的区集合
			List<Continent> ListContinents = continentService.selectByAreaInfo(areaInfo.getAreaName());
			// 封装所有的区数据集合
			List<Areas> listAreas = new ArrayList<Areas>();
			for (Continent continents : ListContinents) {
				// 获取所有的区实体类
				Areas areas = new Areas();
				areas.setKey(continents.getContinentEName());
				areas.setLabel(continents.getContinentCName());
				listAreas.add(areas);
				// 获取所有的国家
				List<Country> listCountry = countryService.selectByContinentArea(continents.getContinentCName());
				// 封装国家数据
				List<IftContinent> listContinent = new ArrayList<IftContinent>();
				for (Country continent : listCountry) {
					// 封装国家数据
					IftContinent con = new IftContinent();
					con.setKey(continent.getCountryCode());
					con.setLabel(continent.getCountryCName());
					listContinent.add(con);// set所有的国家数据
					// 获取所有的城市集合
					List<Airport> listAirport = airportService.queryAirportByCIty(null, null, null,
							continent.getCountryCode(), null);
					// 获取所有的城市
					List<Capital> listCapital = new ArrayList<Capital>();
					for (Airport airport : listAirport) {
						Capital capital = new Capital();
						capital.setKey(airport.getAirportCode());
						capital.setLabel(airport.getAirportCName());
						listCapital.add(capital);// set获取所有的城市
					}
					con.setData(listCapital);// set所有的城市
				}
				areas.setData(listContinent);// set封装国家数据
			}
			states.setData(listAreas);// set封装所有的区数据
		}
		return listStates;
	}

	@Override
	public List<Airline> getAllAirCode() {
		// TODO Auto-generated method stub
		try {
			List<Airline> airlineList = airlineService.queryAirlineList(null);
			SortUtils.sort(airlineList, "airlineCode");
			return airlineList;
		} catch (Exception e) {
			logger.error("获取航司列表发生异常!");
		}
		return null;
	}

	@Override
	public List<Airport> getAllAirport() {
		List<Airport> iftAirportList = null;
		try {
			iftAirportList = airportService.queryAirportByCIty("I",null, null,null,null);
			List<Airport> airportList = airportService.queryAirportByCIty("D",null, null,null,null);
			getSpecialCityNName(airportList);
			iftAirportList.addAll(airportList);
		} catch (Exception e) {
			logger.error("获取国际机场信息异常！",e);
		}
		return iftAirportList;
	}
	
	/**
	 * 城市名称的特殊处理，主要针对北京和上海
	 * 
	 * @return
	 */
	private void getSpecialCityNName(List<Airport> list) {
		if (list != null && !list.isEmpty()) {
			for (Airport airport : list) {
				switch (airport.getAirportCode()) {
				case CITY_CODE_PEK:
					airport.setCityCName(CITY_NAME_PEK);
					break;
				case CITY_CODE_NAY:
					airport.setCityCName(CITY_NAME_NAY);
					break;
				case CITY_CODE_SHA:
					airport.setCityCName(CITY_NAME_SHA);
					break;
				case CITY_CODE_PVG:
					airport.setCityCName(CITY_NAME_PVG);
					break;
				}
			}
		}
	}
	public String getCityDate(String key) {
		Country deparcountry;
		String json = "";
		try {
			if (key.trim().length() == 2) {// 当长度为2标识是国家
				deparcountry = countryService.queryCountryByPram(key);
			} else {// 当长度为3表示是三字码
				deparcountry = airportService.queryCountryByAirport(key);
			}
			if (deparcountry != null && !deparcountry.equals("")) {
				json = "{'area':%s,'city':%s}";
				json = json.format(json, deparcountry.getAreaCode().replace(" ", ""), deparcountry.getCountryCode());
			} else {
				logger.info(key + "基础数据获取到城市信息");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 根据出发城市查询所属的国家

		return json;
	}
}
