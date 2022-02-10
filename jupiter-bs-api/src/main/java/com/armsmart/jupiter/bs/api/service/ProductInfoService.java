package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.ProductInfoAssembler;
import com.armsmart.jupiter.bs.api.dao.ProductInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ProductPicMapper;
import com.armsmart.jupiter.bs.api.dao.ProductVideoMapper;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoPutOnParam;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ProductInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ProductInfo;
import com.armsmart.jupiter.bs.api.entity.ProductPic;
import com.armsmart.jupiter.bs.api.entity.ProductVideo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * ProductInfo service
 *
 * @author 林伟
 **/
@Slf4j
@Service
public class ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductPicMapper picMapper;
    @Autowired
    private ProductVideoMapper videoMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2021/09/15
     */
    public CommonPage<ProductInfoDetail> selectPage(ProductInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfoMapper.selectList(query));
        List<ProductInfoDetail> dtoList = ProductInfoAssembler.INSTANCE.getProductInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 上架
     *
     * @param putOnParam
     * @return com.armsmart.jupiter.bs.api.entity.ProductInfo
     * @date 2021/09/15
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult putOn(ProductInfoPutOnParam putOnParam) {
        ProductInfo entity = ProductInfoAssembler.INSTANCE.getProductInfo(putOnParam);
        productInfoMapper.insert(entity);
        if (!CollectionUtils.isEmpty(putOnParam.getPics())) {
            for (int i = 0; i < putOnParam.getPics().size(); i++) {
                String picUrl = putOnParam.getPics().get(i);
                ProductPic productPic = new ProductPic();
                productPic.setPicUrl(picUrl);
                productPic.setIsDel(false);
                productPic.setProductId(entity.getId());
                productPic.setCreateTime(System.currentTimeMillis());
                productPic.setPicIndex(i);
                picMapper.insert(productPic);
            }
        }
        if (!CollectionUtils.isEmpty(putOnParam.getVideos())) {
            for (int i = 0; i < putOnParam.getVideos().size(); i++) {
                String videoUrl = putOnParam.getVideos().get(i);
                ProductVideo productVideo = new ProductVideo();
                productVideo.setVideoUrl(videoUrl);
                productVideo.setIsDel(false);
                productVideo.setProductId(entity.getId());
                productVideo.setCreateTime(System.currentTimeMillis());
                productVideo.setVideoIndex(i);
                videoMapper.insert(productVideo);
            }
        }
        return CommonResult.success(entity.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResult putDown(String productId) {
        ProductInfo productInfo = productInfoMapper.selectById(Long.valueOf(productId));
        if (null != productInfo) {
            ProductInfo updateEntity = new ProductInfo();
            updateEntity.setId(productInfo.getId());
            updateEntity.setPutOn(false);
            updateEntity.setUpdateTime(System.currentTimeMillis());
            updateEntity.setPutOnTime(System.currentTimeMillis());
            productInfoMapper.updateSelective(updateEntity);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 修改数据
     *
     * @param productInfoUpdateParam
     * @date 2021/09/15
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ProductInfoUpdateParam productInfoUpdateParam) {
        ProductInfo entity = ProductInfoAssembler.INSTANCE.getProductInfo(productInfoUpdateParam);
        productInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2021/09/15
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long id) {
        ProductInfo entity = new ProductInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        productInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2021/09/15
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
        return CommonResult.success();
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.ProductInfoDetail
     * @date 2021/09/15
     */
    public CommonResult<ProductInfoDetail> selectById(Long id) {
        ProductInfo productInfo = productInfoMapper.selectById(id);
        ProductInfoDetail productInfoDetail = ProductInfoAssembler.INSTANCE.getProductInfoDetail(productInfo);
        return CommonResult.success(productInfoDetail);
    }

}
