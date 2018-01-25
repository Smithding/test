package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.GZRefundTicket;

public interface IGZRefundTicketService {

    Page<GZRefundTicket> selectGZRefundTicketReport(Page<GZRefundTicket> page, String air, String ticketNo, String pnr, String cabin, String beginTicketDate, String overTicketDate, String beginFlyDate, String overFlyDate, String depPlace, String arrPlace, String supplier, String customer);
}
