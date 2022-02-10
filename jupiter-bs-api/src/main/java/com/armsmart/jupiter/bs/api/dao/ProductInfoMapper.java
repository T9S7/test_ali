package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ProductInfo;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *ProductInfo mapper接口
 * @author 林伟
 **/
@Mapper
public interface ProductInfoMapper {

	/**
     * 列表查询
	 * @param productInfoQueryParam productInfoQueryParam
	 * @return java.util.List
	 */
	List<ProductInfo> selectList(ProductInfoQueryParam productInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ProductInfo
	 */
	ProductInfo selectById(Serializable id);

    /**
     * 新增
     * @param productInfo productInfo
     * @return int
     */
	int insert(ProductInfo productInfo);

    /**
     * 更新（包含null）
     * @param productInfo productInfo
     * @return int
     */
    int update(ProductInfo productInfo);
    /**
     * 更新（不包含null）
     * @param productInfo productInfo
     * @return int
     */
    int updateSelective(ProductInfo productInfo);
}
