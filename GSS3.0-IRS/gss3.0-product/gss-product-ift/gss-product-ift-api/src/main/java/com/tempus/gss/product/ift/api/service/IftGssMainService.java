package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.GssMain;

public interface IftGssMainService {

    GssMain selectByOrderNo(Long orderNo);
}
