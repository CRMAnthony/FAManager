/*
 * 文 件 名:  Test.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年6月1日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimTempPassCard;
import com.jszt.vo.ValidPassCard;

/**
 * <一句话功能简述>
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Test
{
    public static void main(String[] args) throws JsztException
    {
        List<DimTempPassCard> list = new ArrayList<DimTempPassCard>();
        DimTempPassCard tempCard_1 = new DimTempPassCard();
        tempCard_1.setLicensePlate("苏A12345");
        tempCard_1.setPassPeriod("20161208:5,6");
        list.add(tempCard_1);
        DimTempPassCard tempCard_2 = new DimTempPassCard();
        tempCard_2.setLicensePlate("苏A12345");
        tempCard_2.setPassPeriod("20161208:10,11,12,9,13");
        list.add(tempCard_2);
        Map<String, ValidPassCard> retMap = new HashMap<String, ValidPassCard>();
        if (CollectionUtils.isNotEmpty(list))
        {
            for (DimTempPassCard tempCard : list)
            {
                ValidPassCard passCard = new ValidPassCard();
                String plateLicense = tempCard.getLicensePlate();
                // 临时通行证，一天里可能有两条记录，需要将通行时段拼接起来
                if (retMap.get(plateLicense) != null)
                {
                    ValidPassCard validPassCard = retMap.get(plateLicense);
                    String period = validPassCard.getPeriod();
                    String secPeriod = tempCard.getPassPeriod();
                    // 临时通行证不会跨天
                    String[] dayArray = StringUtils.split(secPeriod, ":");
                    if (dayArray.length == 2)
                    {
                        period = period + "," + dayArray[1];
                    }
                    validPassCard.setPeriod(period);
                    continue;
                }
                passCard.setPlateLicense(plateLicense);
                passCard.setPeriod(tempCard.getPassPeriod());
                retMap.put(plateLicense, passCard);
            }
        }
        for (ValidPassCard validPassCard : retMap.values())
        {
            System.out.println(validPassCard.toString());
        }
        
        
        System.out.println("-----------------------------------------");
        
        Map<String, ValidPassCard> map = new HashMap<String, ValidPassCard>();
        List<ValidPassCard> retList = new ArrayList<ValidPassCard>();
        
        ValidPassCard passCard1 = new ValidPassCard();
        passCard1.setPlateLicense("苏A12345");
        passCard1.setPlateTypeId("1");
        passCard1.setPeriod("1,2,3,4,5,6");
        retList.add(passCard1);
        ValidPassCard passCard2 = new ValidPassCard();
        passCard2.setPlateLicense("苏B12345");
        passCard2.setPlateTypeId("1");
        passCard2.setPeriod("1,6,7,8,9");
        retList.add(passCard2);
        
        
        for (ValidPassCard validPassCard : retList)
        {
            if (!map.containsKey(validPassCard.getPlateLicense()))
            {
                map.put(validPassCard.getPlateLicense(), validPassCard);
            }
        }
        for (ValidPassCard validPassCard : map.values())
        {
            System.out.println(validPassCard.toString());
        }
    }
        
}
