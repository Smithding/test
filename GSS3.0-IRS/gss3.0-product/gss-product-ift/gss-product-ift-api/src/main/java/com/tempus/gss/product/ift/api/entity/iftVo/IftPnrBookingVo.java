package com.tempus.gss.product.ift.api.entity.iftVo;

import java.io.Serializable;

public class IftPnrBookingVo implements Serializable {

    private int pnrType;

    private String content;

    private String airline;

    private String isOutSource;

    private String isCivilTicket;

    @Override
    public String toString() {
        return "IftPnrBookingVo{" +
                "pnrType=" + pnrType +
                ", content='" + content + '\'' +
                ", airline='" + airline + '\'' +
                ", isOutSource='" + isOutSource + '\'' +
                ", isCivilTicket='" + isCivilTicket + '\'' +
                '}';
    }

    public int getPnrType() {
        return pnrType;
    }

    public void setPnrType(int pnrType) {
        this.pnrType = pnrType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getIsOutSource() {
        return isOutSource;
    }

    public void setIsOutSource(String isOutSource) {
        this.isOutSource = isOutSource;
    }

    public String getIsCivilTicket() {
        return isCivilTicket;
    }

    public void setIsCivilTicket(String isCivilTicket) {
        this.isCivilTicket = isCivilTicket;
    }
}
