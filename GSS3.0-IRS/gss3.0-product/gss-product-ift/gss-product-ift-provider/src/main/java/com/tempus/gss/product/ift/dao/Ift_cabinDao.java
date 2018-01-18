package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.Ift_cabin;
import com.tempus.gss.product.ift.api.entity.vo.Ift_cabinVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Component
public interface Ift_cabinDao extends BaseDao<Ift_cabin,Ift_cabinVo>{

    int updateById(Ift_cabin ift_cabin);

    List<Ift_cabin> selectCabinByPolicyNo(Long policy_no);

    int delByPolicyNo(Long policy_no);
}
