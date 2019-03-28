/*
 * 文 件 名:  ConstructionSpeCardServiceImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimConstructionSpeCardDao;
import com.jszt.domain.DimConstructionSpeCard;
import com.jszt.service.ConstructionSpeCardService;
import com.jszt.vo.ConstructionSpeCardReq;

/**
 * 渣土车特殊通行證相关业务接口實現
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConstructionSpeCardServiceImpl implements ConstructionSpeCardService
{
    /**
     * 新增渣土车特殊通行证
     * @param speCardReq
     */
    @Override
    public void addConstructionSpeCard(ConstructionSpeCardReq speCardReq)
    {
        List<DimConstructionSpeCard> conSpeCardList = new ArrayList<DimConstructionSpeCard>();
        String[] licensePlates = StringUtils.split(speCardReq.getLicensePlate(), ",");
        
        DimConstructionSpeCardDao constructionSpeCardDao = ServiceUtil.getService(DimConstructionSpeCardDao.class);
        for(String licensePlate : licensePlates)
        {
            DimConstructionSpeCard dimConstructionSpeCard = new DimConstructionSpeCard();
            
            String id = constructionSpeCardDao.getConstructionSpeCardSEQ();
            
            dimConstructionSpeCard.setId(id);
            dimConstructionSpeCard.setLicensePlate(licensePlate);
            dimConstructionSpeCard.setRegisterDate(speCardReq.getRegisterDate());
            dimConstructionSpeCard.setBeginDate(speCardReq.getBeginDate());
            dimConstructionSpeCard.setEndDate(speCardReq.getEndDate());
            dimConstructionSpeCard.setPassPeriod(speCardReq.getPassPeriod());
            dimConstructionSpeCard.setDescription(speCardReq.getDescription());
            
            conSpeCardList.add(dimConstructionSpeCard);
        }
        
        constructionSpeCardDao.addBatchConstructionSpeCard(conSpeCardList);
    }

    /**
     * 根据车牌号、公司名称查询渣土车特殊通行证信息列表
     * @param req
     * @return
     */
    @Override
    public List<DimConstructionSpeCard> getConstructionSpeCard(ConstructionSpeCardReq req)
    {
        DimConstructionSpeCardDao constructionSpeCardDao = ServiceUtil.getService(DimConstructionSpeCardDao.class);
        return constructionSpeCardDao.getConstructionSpeCard(req);
    }

    /**
     * 根据车牌号删除渣土车
     * @param req
     */
    @Override
    public void deleteConstructionSpeCard(ConstructionSpeCardReq req)
    {
        DimConstructionSpeCardDao constructionSpeCardDao = ServiceUtil.getService(DimConstructionSpeCardDao.class);
        constructionSpeCardDao.deleteConstructionSpeCard(req.getIds());
    }

    /**
     * 根据车牌号修改渣土车
     * @param dimConstructionSpeCard
     */
    @Override
    public void updateConstructionSpeCard(ConstructionSpeCardReq speCardReq)
    {
        DimConstructionSpeCardDao constructionSpeCardDao = ServiceUtil.getService(DimConstructionSpeCardDao.class);
        constructionSpeCardDao.updateConstructionSpeCard(speCardReq);
    }

    /**
     * 获取某一天有效的渣土车特殊通行证
     * @param date
     * @return
     */
    @Override
    public List<DimConstructionSpeCard> getValidConSpeCards(int date)
    {
        DimConstructionSpeCardDao constructionSpeCardDao = ServiceUtil.getService(DimConstructionSpeCardDao.class);
        return constructionSpeCardDao.getValidConSpeCards(date);
    }
}
