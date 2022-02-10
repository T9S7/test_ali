package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ProductPic;
import com.armsmart.jupiter.bs.api.dto.request.ProductPicQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *ProductPic mapper接口
 * @author 林伟
 **/
@Mapper
public interface ProductPicMapper {

	/**
     * 列表查询
	 * @param productPicQueryParam productPicQueryParam
	 * @return java.util.List
	 */
	List<ProductPic> selectList(ProductPicQueryParam productPicQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ProductPic
	 */
	ProductPic selectById(Serializable id);

    /**
     * 新增
     * @param productPic productPic
     * @return int
     */
	int insert(ProductPic productPic);

    /**
     * 更新（包含null）
     * @param productPic productPic
     * @return int
     */
    int update(ProductPic productPic);
    /**
     * 更新（不包含null）
     * @param productPic productPic
     * @return int
     */
    int updateSelective(ProductPic productPic);
}
