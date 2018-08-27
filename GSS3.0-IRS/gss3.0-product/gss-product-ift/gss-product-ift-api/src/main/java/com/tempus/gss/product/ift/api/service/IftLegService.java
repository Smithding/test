package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.vo.LegVo;

import java.util.List;

public interface IftLegService {

    /**
     * 根据legNo查询leg
     */
    public List<Leg> selectLegListByLegNoList(List<Long> legList);
}
