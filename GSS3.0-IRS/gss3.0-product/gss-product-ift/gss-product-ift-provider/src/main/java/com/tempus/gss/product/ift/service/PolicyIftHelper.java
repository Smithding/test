package com.tempus.gss.product.ift.service;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.tempus.gss.product.ift.api.entity.Policy;
import com.tempus.gss.product.ift.api.entity.exception.Errors;
import com.tempus.gss.product.ift.api.entity.exception.ProductException;
import com.tempus.gss.product.ift.api.entity.helper.ExcelHelper;
import com.tempus.gss.util.EntityUtil;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;

/*
 * 国际政策批量导入处理类
 */
@Service(value = "product.ift.PolicyIftHelper")
public class PolicyIftHelper {
	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(PolicyIftHelper.class);
	//普通 参数列表， 需要与导入的excel模板中对应 index 对应
	public Map<Integer, String> setSingleCommonMap() {
		Map<Integer, String> includeds = new HashMap<Integer, String>();
		includeds.put(0, "id");
		includeds.put(1, "policyNo");
		includeds.put(2, "owner");
		includeds.put(3, "policyType");
		includeds.put(4, "airline");
		includeds.put(5, "distributor");
		includeds.put(6, "productor");
		includeds.put(7, "saleConfig");
		includeds.put(8, "ticketWay");
		includeds.put(9, "productNo");
		includeds.put(10, "travellerType");
		includeds.put(11,"travellerLimit");
		includeds.put(12,"teamTicketType");
		includeds.put(13, "tripType");
		includeds.put(14, "goStart");
		includeds.put(15, "exceptGoStart");
		includeds.put(16, "goEnd");
		includeds.put(17, "exceptGoEnd");
		includeds.put(18, "backEnd");
		includeds.put(19, "exceptBackEnd");
		includeds.put(20, "allowCrossLineMatch");
		includeds.put(21, "travelDate");
		includeds.put(22, "backDate");
		includeds.put(23, "ticketDate");
		includeds.put(24, "isSameCarrier");
		includeds.put(25, "isCodeShare");
		includeds.put(26, "shareAirline");
		includeds.put(27, "onlyDirectFlight");
		includeds.put(28, "goNotFlight");
		includeds.put(29, "goMustFlight");
		includeds.put(30, "backNotFlight");
		includeds.put(31, "backMustFlight");
		includeds.put(32, "goFlightNo");
		includeds.put(33, "backFlightNo");
		includeds.put(34, "agencyFee");
		includeds.put(35, "policyEffectStart");
		includeds.put(36, "policyEffectEnd");
		includeds.put(37, "remark");
		includeds.put(38, "status");
		includeds.put(39, "advPnr");
		includeds.put(40, "isDefault");
		includeds.put(41, "batchNum");
		includeds.put(42, "cabin");
		includeds.put(43, "saleRebate");
		includeds.put(44, "saleOwBrokerage");
		includeds.put(45, "saleRtBrokerage");
		includeds.put(46, "buyRebate");
		includeds.put(47, "buyOwBrokerage");
		includeds.put(48, "buyRtBrokerage");
		return includeds;
	}
	
	
	//普通的数据进行校验 并重新验证之后的信息
	public Boolean getValuesForSingCommon(Map<String, Object> map, Row row, Map<Integer, String> paramMap,boolean isRightFlag, XSSFWorkbook xssfWorkbook) {
		try{
			int columIndex = 0; // 当前执行校验时的
			for (Cell cell : row) {
				columIndex = cell.getColumnIndex();
				logger.info(">>>" + columIndex + "  >>>" + ExcelHelper.getCellValue(cell, 1));
				String cellValue = ExcelHelper.getCellValue(cell, 1);
				if(StringUtils.isNotEmpty(cellValue)){
					String cellName = paramMap.get(columIndex);
					StringBuilder sb = new StringBuilder();
					String err = "";
					err = checkIsRight(cellName, cellValue);
					if (null != err && !"".equals(err)) {
						isRightFlag = false;
						sb.append(cellValue).append("错误提示：").append(err);
						ExcelHelper.getCellFont(xssfWorkbook, cell, err);
					}else {
						map.put(cellName, cellValue);
					}
					if(paramMap.size()-2==columIndex){
						break;
					}else{
						columIndex++;
					}
				} else {
					continue;
				}
			}
		}catch(Exception e){
			isRightFlag = false;
			row.createCell(24).setCellValue("政策批量导入解析异常"+e);
			logger.error("政策批量导入解析异常",e);
		}
		return isRightFlag;
	}

	
	// 对传入的每一项进行对应的校验 如果校验通过 返回空字符串 不通过则返回对应的错误信息
	public String checkIsRight(String colunmName, String cellValue) {
			String errorMsg = "";
			switch (colunmName) {
			case "airline":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "航司不能为空";
				}else if(cellValue.toString().length() > 2) {
					errorMsg = "航司二字码不正确";
				}
				break;
			case "saleConfig":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "销售配置不能为空";
				}
				break;
			case "travellerType":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "旅客类型不能为空";
				}
				break;
			case "travellerLimit":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "旅客人数下限不能为空";
				}
				break;
			case "teamTicketType":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "团队出票类型不能为空";
				}
				break;
			case "tripType":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "行程类型不能为空";
				}
				break;
			case "goStart":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "去程起点不能为空";
				}
				break;
			case "goEnd":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "去程终点/折返点不能为空";
				}
				break;
			case "travelDate":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "去程旅行日期不能为空";
				}
//				if("0".equals(cellValue.toString())){
//					errorMsg = "去程旅行日期格式不正确";
//				}
				break;
			case "backDate":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "回程旅行日期不能为空";
				}
//				if("0".equals(cellValue.toString())){
//					errorMsg = "回程旅行日期格式不正确";
//				}
				break;
			case "ticketDate":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "出票日期不能为空";
				}
//				if("0".equals(cellValue.toString())){
//					errorMsg = "出票日期格式不正确";
//				}
				break;
			case "agencyFee":
					Double rebate = Double.parseDouble(cellValue.toString());
					if (rebate > 99.9 || rebate < 0.0) {
						errorMsg = "代理费率不在正常范围内，请检查（0-99.9之间）";
					}
				break;
			case "policyEffectStart":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "政策开始日期不能为空";
				}
//				if("0".equals(cellValue.toString())){
//					errorMsg = "政策开始日期格式不正确";
//				}
				break;
			case "policyEffectEnd":
				if (StringUtils.isBlank(cellValue)) {
					errorMsg = "政策结束日期不能为空";
				}
//				if("0".equals(cellValue.toString())){
//					errorMsg = "政策结束日期格式不正确";
//				}
				break;
			case "ticketWay":
				if(!StringUtils.isBlank(cellValue)){
					String types = cellValue.toString().trim();
					//表示填写了两个出票方式
					if(types.indexOf("/")!=-1){
						String [] ticketType = types.split("\\/");
						for (String type : ticketType) {
							if(!type.toUpperCase().equals("B2B")&&!type.toUpperCase().equals("BSP")){
								errorMsg = "请输入正确的出票方式（格式：B2B/BSP）";
							}
						}
					}else{
						if(!types.toUpperCase().equals("B2B")&&!types.toUpperCase().equals("BSP")){
							errorMsg = "请输入正确的出票方式（格式：B2B/BSP）";
						}
					}
				}else{
					errorMsg = "出票类型不能为空（B2B/BSP）";
				}
				break;
			default:
				break;
			}
			return errorMsg;
	}
	
	// 将map 中针对每种产品可用的参数信息附加到指定的产品实体上 并对参数进行校验
	public Policy convertToEntity(Agent agent, Map<String, Object> map) {
			Policy policy = new Policy();
			// 可以忽略大小写
		if (map != null) {
			if (map.containsKey("airline")
					&& map.containsKey("saleConfig") && map.containsKey("travellerType") && map.containsKey("travellerLimit") && map.containsKey("teamTicketType")
					&& map.containsKey("tripType") && map.containsKey("goStart") && map.containsKey("goEnd") && map.containsKey("travelDate")
					&& map.containsKey("backDate") && map.containsKey("ticketDate") && map.containsKey("agencyFee") && map.containsKey("policyEffectStart")
					&& map.containsKey("policyEffectEnd") && map.containsKey("ticketWay")) {
				String jsonStr = JsonUtil.toJson(map);
				try {
					policy = JsonUtil.toBean(jsonStr, Policy.class);
					if (null != policy) {
						EntityUtil.setAddInfo(policy, agent); // 添加必需信息
					}
				} catch (Exception e) {
					logger.error("convertToEntity", e);
					throw new ProductException(Errors.E_UNKNOWN); // 缺少必要参数
				}
				return policy;
			} else {
				throw new ProductException(Errors.E_INVALID_PARAM); // 缺少必要参数
			}
		} else {
			throw new ProductException(Errors.E_INVALID_PARAM);
		}
	}
}
