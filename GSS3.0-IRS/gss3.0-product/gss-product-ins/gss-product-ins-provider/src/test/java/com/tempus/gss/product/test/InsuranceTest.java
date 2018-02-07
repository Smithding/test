///**
// * InsuranceTest.java
// * com.tempus.gss.product.test
// *
// * Function： TODO
// *
// * ver date author
// * ──────────────────────────────────
// * 2016年10月26日 shuo.cheng
// *
// * Copyright (c) 2016, TNT All Rights Reserved.
// */
//
//package com.tempus.gss.product.test;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Repeat;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.tempus.gss.product.common.entity.RequestWithActor;
//import com.tempus.gss.product.ins.Application;
//import com.tempus.gss.product.ins.api.service.IInsuranceService;
//
///**
// * ClassName:InsuranceTest Function: TODO ADD FUNCTION Reason: TODO ADD REASON
// *
// * @author shuo.cheng
// * @version
// * @since Ver 1.1
// * @Date 2016年10月26日 上午9:51:41
// *
// * @see
// * 
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class)
//@WebAppConfiguration
//public class InsuranceTest {
//
//	@Autowired
//	private IInsuranceService insuranceService;
//
//	//	@Test
//	//	public void getOrderTest() {
//	//
//	//		RequestWithActor<Long> saleOrderNo = new RequestWithActor<Long>();
//	//		// List<SaleOrderExt> insuranceList =
//	//		// ItemDataUtil.getItemList(SaleOrderExt.class, 1);
//	//		// SaleOrderExt insurance = insuranceList.get(0);
//	//		// saleOrderNo.setEntity(insurance.getSaleOrderNo());
//	//		orderService.buyInsure(saleOrderNo);
//	//		System.out.println("----------------");
//	//	}
//
////	@Test
////	@Repeat(1)
////	public void cancelOrderTest() {
////
////		RequestWithActor<OrderCancelVo> orderCancelVo = new RequestWithActor<OrderCancelVo>();
////		OrderCancelVo vo = new OrderCancelVo();
////		vo.setPolicyNo("06010016131158201600001347");
////		orderCancelVo.setEntity(vo);
////		orderService.cancelSaleOrder(orderCancelVo);
////	}
//
//	// @Test
//	// @Repeat(1)
//	// public void createInsurance() {
//	//
//	// RequestWithActor<Insurance> insurance = new
//	// RequestWithActor<Insurance>();
//	// // List<Insurance> insuranceList =
//	// // ItemDataUtil.getItemList(Insurance.class, 1);
//	// // Insurance insurance = insuranceList.get(0);
//	// // requestWithActor.setEntity(insurance);
//	// insuranceService.createInsurance(insurance);
//	// }
//
//	@Test
//	@Repeat(1)
//	public void cancelOrderTest() {
//
//		RequestWithActor<String> insureType = new RequestWithActor<String>();
//		insureType.setEntity("1");
//		insuranceService.getDefault(insureType);
//	}
//}
