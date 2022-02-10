package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ProductVideo;
import com.armsmart.jupiter.bs.api.dto.request.ProductVideoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *ProductVideo mapper接口
 * @author 林伟
 **/
@Mapper
public interface ProductVideoMapper {

	/**
     * 列表查询
	 * @param productVideoQueryParam productVideoQueryParam
	 * @return java.util.List
	 */
	List<ProductVideo> selectList(ProductVideoQueryParam productVideoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ProductVideo
	 */
	ProductVideo selectById(Serializable id);

    /**
     * 新增
     * @param productVideo productVideo
     * @return int
     */
	int insert(ProductVideo productVideo);

    /**
     * 更新（包含null）
     * @param productVideo productVideo
     * @return int
     */
    int update(ProductVideo productVideo);
    /**
     * 更新（不包含null）
     * @param productVideo productVideo
     * @return int
     */
    int updateSelective(ProductVideo productVideo);
}
