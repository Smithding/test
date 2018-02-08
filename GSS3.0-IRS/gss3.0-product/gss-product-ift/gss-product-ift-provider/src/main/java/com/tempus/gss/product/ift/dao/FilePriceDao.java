package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.FilePrice;
import com.tempus.gss.product.ift.api.entity.vo.FilePriceVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Component
public interface FilePriceDao extends BaseDao<FilePrice,FilePriceVo> {

    int deleteByBean (FilePrice filePrice);

    int updateByIds(FilePrice filePrice);

    List<FilePrice> queryById(Long id);

    List<FilePrice> queryObjByOD (FilePrice filePrice);
}
