package com.tempus.gss.product.hol.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HolStatement;
import com.tempus.gss.product.hol.api.entity.vo.QueryStatementVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */
public interface HolStatementMapper extends AutoMapper<HolStatement> {

    List<HolStatement> queryList(Page<HolStatement> page, QueryStatementVo queryStatementVo);
}
