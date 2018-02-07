package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

public class PnrPassenger implements Serializable  {
    /**
	 * 
	 */
	/**
	 * pnr中的名字
	 */
	private String namePNR;
	private static final long serialVersionUID = 6972733992543588343L;
    private String passengername;//乘机人姓名
    private String passengersex; //乘机人性别 ( MR MS  CHD  OTHER)
    private String passengernationality; //乘机人国籍
    private String passengeridentitynumber; //乘机人证件号码
    private String passengerbirthday; //乘机人出生日期
    private String ticketnumber;  //票号
    private String passengerpassportvalidity; //护照有效期
    private String passengeridentitytype; //证件类型
    private String passengerstype;	//乘机人类型
    private String phone;//乘机人手机号

    public String getPassengername() {
        return passengername;
    }


    public void setPassengername(String passengername) {
        this.passengername = passengername == null ? null : passengername.trim();
    }


    public String getPassengersex() {
        return passengersex;
    }


    public void setPassengersex(String passengersex) {
        this.passengersex = passengersex == null ? null : passengersex.trim();
    }


    public String getPassengernationality() {
        return passengernationality;
    }


    public void setPassengernationality(String passengernationality) {
        this.passengernationality = passengernationality == null ? null : passengernationality.trim();
    }


    public String getPassengeridentitynumber() {
        return passengeridentitynumber;
    }


    public void setPassengeridentitynumber(String passengeridentitynumber) {
        this.passengeridentitynumber = passengeridentitynumber == null ? null : passengeridentitynumber.trim();
    }


    public String getPassengerbirthday() {
        return passengerbirthday;
    }


    public void setPassengerbirthday(String passengerbirthday) {
        this.passengerbirthday = passengerbirthday == null ? null : passengerbirthday.trim();
    }


    public String getTicketnumber() {
        return ticketnumber;
    }


    public void setTicketnumber(String ticketnumber) {
        this.ticketnumber = ticketnumber == null ? null : ticketnumber.trim();
    }


    public String getPassengerpassportvalidity() {
        return passengerpassportvalidity;
    }


    public void setPassengerpassportvalidity(String passengerpassportvalidity) {
        this.passengerpassportvalidity = passengerpassportvalidity == null ? null : passengerpassportvalidity.trim();
    }


    public String getPassengeridentitytype() {
        return passengeridentitytype;
    }


    public void setPassengeridentitytype(String passengeridentitytype) {
        this.passengeridentitytype = passengeridentitytype == null ? null : passengeridentitytype.trim();
    }

    public String getPassengerstype() {
        return passengerstype;
    }

    public void setPassengerstype(String passengerstype) {
        this.passengerstype = passengerstype == null ? null : passengerstype.trim();
    }

	public String getNamePNR() {
		return namePNR;
	}

	public void setNamePNR(String namePNR) {
		this.namePNR = namePNR;
	}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}