package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.FilePrice;
import com.tempus.gss.product.ift.api.entity.vo.FilePriceVo;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public interface IFilePriceService {

    Page<FilePrice> pageList(Page<FilePrice> page,RequestWithActor<FilePriceVo> requestWithActor);

    FilePrice queryById(Long id);

    int update(FilePrice filePrice);

    int insert(FilePrice filePrice);

    int deleteByBean(FilePrice filePrice);
}
