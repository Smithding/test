package com.tempus.gss.product.hol.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.toolkit.CollectionUtil;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.contant.OperateEnum;
import com.tempus.gss.product.hol.api.contant.QueryProperty;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.syn.IHolMongoQuery;

@Service
public class HolMongoQuery implements IHolMongoQuery {

	@Autowired
	MongoTemplate mongoTemplate1;
	
	public <T> List<T> findByQuery(Query query, Class<T> clazz) {
		return mongoTemplate1.find(query, clazz);
	}
	
	
	@Override
	public <T> TCResponse<T> findByProperty(QueryProperty queryProperty, Pageable pageable, Class<T> clazz) {
			//Pagination<T> pagination = new Pagination<T>();
			TCResponse<T> pagination = new TCResponse<T>();
			//PaginationCondition pc = new PaginationCondition(pageable.getPageNumber(), pageable.getPageSize());
			int page = pageable.getPageNumber();
			int pageSize = pageable.getPageSize();

			Query query = new Query();

			if (CollectionUtil.isNotEmpty(queryProperty.getProNames())) {
				Criteria criteria = createCriteria(queryProperty);
				query.addCriteria(criteria);
			}
			long totalCount = mongoTemplate1.count(query, clazz);
			query.skip(page-1);// skip相当于从那条记录开始
			query.limit(pageSize);// 从skip开始,取多少条记录
			Sort sort = pageable.getSort();
			if (sort != null) {
				query.with(sort);
			}
			List<T> items = mongoTemplate1.find(query, clazz);
			pagination.setTotalCount(NumberUtils.toInt(totalCount + "", 0));
			int maxPage = 0;
			if (totalCount == 0) {
				maxPage = 0;
			} else if (totalCount <= pageSize) {
				maxPage = 1;
			} else {
				if (pageSize == 0)
					pageSize = 10;
				maxPage = NumberUtils.toInt(totalCount + "", 0) / pageSize + 1;
			}
			pagination.setTotalPatge(maxPage);
			pagination.setResponseResult(items);
			/*pagination.setMaxPage(maxPage);
			pagination.setItems(items);
			pagination.setPage(page);
			pagination.setPageSize(pageSize);*/
			return pagination;
		}
		
		public Criteria createCriteria(QueryProperty queryProperty) {
			LinkedList<String> proNames = queryProperty.getProNames();
			LinkedList<OperateEnum> operates = queryProperty.getOperates();
			LinkedList<Object> values = queryProperty.getValues();

			Criteria criteria = null;
			String proName = null;
			OperateEnum operate = null;
			Object value = null;

			for (int i = 0; i < proNames.size(); i++) {
				proName = proNames.get(i);
				operate = operates.get(i);
				value = values.get(i);
				if(operate.equals(OperateEnum.OROPERATOR)) {
					if(i == 0) {
						criteria = new Criteria(proName);
						String[] split = value.toString().split(",");
						criteria.orOperator(Criteria.where(proName).regex(".*?\\" +split[0]+ ".*"), Criteria.where(proName).regex(".*?\\" +split[1]+ ".*"));
					} else {
						String[] split = value.toString().split(",");
						criteria.orOperator(Criteria.where(proName).regex(".*?\\" +split[0]+ ".*"), Criteria.where(proName).regex(".*?\\" +split[1]+ ".*"));
					}
				}else {
					if (i == 0) {
						criteria = new Criteria(proName);
					} else {
						criteria = criteria.and(proName);
					}
					switch (operate) {
					case GT:
						criteria.gt(value);
						break;
					case LT:
						criteria.lt(value);
						break;
					case GTE:
						criteria.gte(value);
						break;
					case LTE:
						criteria.lte(value);
						break;
					case EQ:
						criteria.is(value);
						break;
					case NE:
						criteria.ne(value);
						break;
					case IN:
						criteria.in(value);
						break;
					case NIN:
						criteria.nin(value);
						break;
					case NOT:
						criteria.not();
						break;
					case ALL:
						criteria.all(value);
						break;
					case REGEX:
						criteria.regex(escapeRegexString(String.valueOf(value)), "i");
						break;
					case BETWEENLONG:
						String val = String.valueOf(value);
						long from = NumberUtils.toLong(val.split(",")[0]);
						long to = NumberUtils.toLong(val.split(",")[1]);
						criteria.gte(from).lte(to);
						break;
					default:
						criteria.is(value);
						break;
					}
				}
				
			}
			return criteria;
		}
		
		/**
		 * 把字符串中含有正则表达式特殊意义的字符转义
		 * @param value
		 * @return
		 */
		public String escapeRegexString(String value){
			final char[] reserved={'.','$','^','{','}','[',']','(',')','|','*','+','?','\\'};
			char c;
			boolean bingo=false;
			StringBuilder sb=new StringBuilder(value);
			for(int i=0; i<sb.length(); i++){
				bingo=false;
				c=sb.charAt(i);
				for(int j=0; j<reserved.length; j++){
					if(c==reserved[j]){
						bingo=true;
						break;
					}
				}
				if(bingo){
					sb.insert(i, '\\');
					i++;
				}
			}
			return sb.toString();
		}
		
		/**
		 * @param proName
		 * @param operat
		 * @param value
		 * @return
		 */
		public Criteria createCriteria(String proName, OperateEnum operat, Object value) {
			String thirdProName = "";
			if(proName.contains(",")) {
				String[] split = proName.split(",");
				proName = split[0];
				thirdProName = split[1];
			}
			Criteria criteria = Criteria.where(proName);
			switch (operat) {
			case GT:
				criteria.gt(value);
				break;
			case LT:
				criteria.lt(value);
				break;
			case GTE:
				criteria.gte(value);
				break;
			case LTE:
				criteria.lte(value);
				break;
			case EQ:
				criteria.is(value);
				break;
			case NE:
				criteria.ne(value);
				break;
			case IN:
				if (value instanceof Collection) {
					criteria.in((Collection<?>) value);
				} else if (value.getClass().isArray()) {
					Object[] array = (Object[]) value;
					criteria.in(Arrays.asList(array));
				} else {
					criteria.in(value);
				}
				break;
			case NIN:
				if (value instanceof Collection) {
					criteria.nin((Collection<?>) value);
				} else if (value.getClass().isArray()) {
					Object[] array = (Object[]) value;
					criteria.nin(Arrays.asList(array));
				} else {
					criteria.nin(value);
				}
				break;
			case NOT:
				criteria.not();
				break;
			case ALL:
				criteria.all(value);
				break;
			case REGEX:
				criteria.regex(escapeRegexString(String.valueOf(value)), "i");
				break;
			case ELEMMATCH:
				Criteria criatiraCityScenic = new Criteria();
				criatiraCityScenic.and(thirdProName).regex(escapeRegexString(String.valueOf(value)), "i");
				criteria.elemMatch(criatiraCityScenic);
				break;
			case BETWEENLONG:
				String val = String.valueOf(value);
				long from = NumberUtils.toLong(val.split(",")[0]);
				long to = NumberUtils.toLong(val.split(",")[1]);
				criteria.gte(from).lte(to);
				break;
			default:
				criteria.is(value);
				break;
			}
			return criteria;
		}
		
		@Override
		public List<HolMidBaseInfo> queryAlikeHol(String lon, String lat, Set<String> phoneList) {
			
			List<HolMidBaseInfo> res = null;
			Query query =new Query();
			Criteria cr =new Criteria();
			query.skip(0);
			query.limit(3);
			if(StringUtil.isNotNullOrEmpty(lon) && StringUtil.isNotNullOrEmpty(lat)) {
				Point point =new Point(Double.valueOf(lon), Double.valueOf(lat));
				cr.and("resPosition").near(point).maxDistance(0.0005D);//100000/6378137
			}
			/*if(StringUtil.isNotBlank(lon)) {
				cr.and("lon").regex("^" + lon + ".*$");
			}else {
				throw new GSSException("参数经度为空", "0401", "匹配中间表失败,参数经度为空");
			}
			if(StringUtil.isNotBlank(lat)) {
				cr.and("lat").regex("^" + lat + ".*$");
			}else {
				throw new GSSException("参数纬度", "0118", "匹配中间表失败,参数纬度为空");
			}*/
			if(StringUtil.isNotNullOrEmpty(phoneList)) {
				List<Criteria> list = new ArrayList<Criteria>();
				for(String sss : phoneList) {
					Criteria c1 =new Criteria();
					c1 = Criteria.where("resPhone").regex("^.*"+sss+".*$");
					list.add(c1);
				}
				Criteria[] array = list.toArray(new Criteria[list.size()]);
				cr.orOperator(array);
			}
			query.addCriteria(cr);
			res = mongoTemplate1.find(query, HolMidBaseInfo.class);
			
			return res;
		}
	

}
