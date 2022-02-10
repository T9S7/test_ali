package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.StoreArtInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.StoreArtFullInfo;
import com.armsmart.jupiter.bs.api.entity.StoreArtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *StoreArtInfo mapper接口
 * @author wei.lin
 **/
@Mapper
public interface StoreArtInfoMapper {

	/**
     * 列表查询
	 * @param storeArtInfoQueryParam storeArtInfoQueryParam
	 * @return java.util.List
	 */
	List<StoreArtInfo> selectList(StoreArtInfoQueryParam storeArtInfoQueryParam);


	List<StoreArtFullInfo> selectStoreList(StoreArtInfoQueryParam storeArtInfoQueryParam);

	List<StoreArtFullInfo> selectArtList();

	List<StoreArtFullInfo> selectArtTopList();
	/**
	 * 根据艺术品ID查询
	 * @param artId 艺术品ID
	 * @param storeId 店铺ID
	 * @return StoreArtInfo
	 */
	StoreArtFullInfo selectStoreArtInfo(@Param("artId") Long artId, @Param("storeId") Long storeId);
	/**
	* 根据主键查询
	* @param id
	* @return StoreArtInfo
	*/
	StoreArtInfo selectById(Long id);

    /**
     * 新增
     * @param storeArtInfo storeArtInfo
     * @return int
     */
	int insert(StoreArtInfo storeArtInfo);

    /**
     * 更新（包含null）
     * @param storeArtInfo storeArtInfo
     * @return int
     */
    int update(StoreArtInfo storeArtInfo);
    /**
     * 更新（不包含null）
     * @param storeArtInfo storeArtInfo
     * @return int
     */
    int updateSelective(StoreArtInfo storeArtInfo);

	/**
	 * 根据艺术品ID物理删除
	 * @param artId
	 * @return void
	 */
	void physicalDelByArtId(Long artId);
}
