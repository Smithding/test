package com.tempus.gss.product.ift.api.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangwei on 2016/10/13.
 */
public class CabinsPricesTotals implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 航线仓位集合.
	 */
	private String cabins;
	/**
	 * 航线仓位数量.
	 */
	private Integer cabinsCount;


	/**
	 * 是否大客户价格.
	 */
	private boolean isVipPrice;

	/**
	 * 大客户号.
	 */
	private String vipCode;

	/**
	 * 票规
	 */
	private String ticketRule;

	private List<PassengerTypePricesTotal> passengerTypePricesTotals;

	/**销售价总和*/
	private Double salePriceCount;

	/**票面价总和*/
	private Double fareCount;

	/**优惠金额总和*/
	private Double favorableCount;

	/**税费总和*/
	private Double taxCount;

	/**各个乘客类型数量统计*/
	private Map<String,Integer> passengerTypeCount;

	/**各个乘客类型的价格统计**/
	private Map<String,Double> passengerTypePrice;

	/**各个乘客类型的税费统计**/
	private Map<String,Double> passengerTypeTax;

	/**各个乘客类型的价格统计**/
	private Map<String,Double> passengerTypeSalePrice;
	/**
	 * 直减价格
	 */
    private BigDecimal oneWayPrivilege;

	public BigDecimal getOneWayPrivilege() {
		return oneWayPrivilege;
	}

	public BigDecimal setOneWayPrivilege(BigDecimal oneWayPrivilege) {
		BigDecimal a = new BigDecimal(0);
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal pricesTotal:this.passengerTypePricesTotals){
				if(pricesTotal.getOneWayPrivilege()!=null)
				a = a.add(oneWayPrivilege);
			}
		}
		return a;
	}
	public String getCabins() {

		return cabins;
	}

	public void setCabins(String cabins) {

		this.cabins = cabins;
	}

	public Integer getCabinsCount() {

		return cabinsCount;
	}

	public void setCabinsCount(Integer cabinsCount) {

		this.cabinsCount = cabinsCount;
	}

	public boolean isVipPrice() {

		return isVipPrice;
	}

	public void setVipPrice(boolean vipPrice) {

		isVipPrice = vipPrice;
	}

	public String getVipCode() {

		return vipCode;
	}

	public void setVipCode(String vipCode) {

		this.vipCode = vipCode;
	}

	public List<PassengerTypePricesTotal> getPassengerTypePricesTotals() {

		return passengerTypePricesTotals;
	}

	public void setPassengerTypePricesTotals(List<PassengerTypePricesTotal> passengerTypePricesTotals) {

		this.passengerTypePricesTotals = passengerTypePricesTotals;
	}

	public String getTicketRule() {

		return ticketRule;
	}

	public void setTicketRule(String ticketRule) {

		this.ticketRule = ticketRule;
	}

	public Double getSalePriceCount() {
		double a = 0;
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal pricesTotal:this.passengerTypePricesTotals){
				if(pricesTotal.getSalePrice()!=null)
				a+=pricesTotal.getSalePrice().doubleValue();
			}
		}
		return a;
	}
	public void setSalePriceCount(Double salePriceCount) {
		this.salePriceCount = salePriceCount;
	}

	public Double getFareCount() {
		double a = 0;
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal pricesTotal:this.passengerTypePricesTotals){
				if(pricesTotal.getFare()!=null)
				a+=pricesTotal.getFare().doubleValue();
			}
		}
		return a;
	}

	public void setFareCount(Double fareCount) {
		this.fareCount = fareCount;
	}

	public Double getFavorableCount() {
		double a = 0;
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal pricesTotal:this.passengerTypePricesTotals){
				if(pricesTotal.getFavorable()!=null)
				a+=pricesTotal.getFavorable().doubleValue();
			}
		}
		return a;
	}

	public void setFavorableCount(Double favorableCount) {
		this.favorableCount = favorableCount;
	}

	public Double getTaxCount() {
		double a = 0;
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal pricesTotal:this.passengerTypePricesTotals){
				if(pricesTotal.getTax()!=null)
				a+=pricesTotal.getTax().doubleValue();
			}
		}
		return a;
	}

	public void setTaxCount(Double taxCount) {
		this.taxCount = taxCount;
	}

	public Map<String, Integer> getPassengerTypeCount() {
		Map<String, Integer> passengerTypeMap = new HashMap<>();
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal passengerType:this.passengerTypePricesTotals){
				Integer num = passengerTypeMap.get(passengerType.getPassengerType());
				if(num==null){
					passengerTypeMap.put(passengerType.getPassengerType(),1);
				}else{
					passengerTypeMap.put(passengerType.getPassengerType(),num+1);
				}
			}
		}
		return passengerTypeMap;
	}

	public void setPassengerTypeCount(Map<String,Integer> passengerTypeCount) {
		this.passengerTypeCount = passengerTypeCount;
	}

	public void setPassengerTypePrice(Map<String, Double> passengerTypePrice) {
		this.passengerTypePrice = passengerTypePrice;
	}

	public Map<String, Double> getPassengerTypePrice() {
		Map<String, Double> passengerTypeMap = new HashMap<>();
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal passengerType:this.passengerTypePricesTotals){
				passengerTypeMap.put(passengerType.getPassengerType(),passengerType.getFare()==null?0d:passengerType.getFare().doubleValue());
			}
		}
		return passengerTypeMap;
	}

	public Map<String, Double> getPassengerTypeTax() {
		Map<String, Double> passengerTypeMap = new HashMap<>();
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal passengerType:this.passengerTypePricesTotals){
				passengerTypeMap.put(passengerType.getPassengerType(),passengerType.getTax()==null?0d:passengerType.getTax().doubleValue());
			}
		}
		return passengerTypeMap;
	}

	public void setPassengerTypeTax(Map<String, Double> passengerTypeTax) {
		this.passengerTypeTax = passengerTypeTax;
	}

	public Map<String, Double> getPassengerTypeSalePrice() {
		Map<String, Double> passengerTypeMap = new HashMap<>();
		if(this.passengerTypePricesTotals!=null){
			for(PassengerTypePricesTotal passengerType:this.passengerTypePricesTotals){
				if(passengerType.getSalePrice()==null){
					Double price = (passengerType.getTax()==null?0d:passengerType.getTax().doubleValue())+(passengerType.getFare()==null?0d:passengerType.getFare().doubleValue());
					passengerTypeMap.put(passengerType.getPassengerType(),price);
				}else{
					passengerTypeMap.put(passengerType.getPassengerType(),passengerType.getSalePrice().doubleValue());
				}
			}
		}
		return passengerTypeMap;
	}

	public void setPassengerTypeSalePrice(Map<String, Double> passengerTypeSalePrice) {
		this.passengerTypeSalePrice = passengerTypeSalePrice;
	}
}
