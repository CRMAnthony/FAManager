/*
 * 文 件 名:  DeleteRecordReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年9月9日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 删除记录请求类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年9月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DeleteRecordReq
{
    /**
     * 处理状态
     */
    private int processState;
    
    /**
     * 删除数据字符串  时间：车牌:禁区编号,时间：车牌:禁区编号
     */
    private String delStr;
    
    public List<IllegalApproval> getDatas()
    {
        List<IllegalApproval> ret = new ArrayList<IllegalApproval>(15);
        String[] delDatas = StringUtils.split(delStr, ",");
        for (String string : delDatas)
        {
            IllegalApproval record = new IllegalApproval();
            String[] tempRecord = StringUtils.split(string, ":");
            record.setDateKey(tempRecord[0]);
            record.setLicensePlate(tempRecord[1]);
            record.setAreaId(tempRecord[2]);
            
            ret.add(record);
        }
        return ret;
    }

    /**
     * @return 返回 processState
     */
    public int getProcessState()
    {
        return processState;
    }

    /**
     * @param 对processState进行赋值
     */
    public void setProcessState(int processState)
    {
        this.processState = processState;
    }

    /**
     * @return 返回 delStr
     */
    public String getDelStr()
    {
        return delStr;
    }

    /**
     * @param 对delStr进行赋值
     */
    public void setDelStr(String delStr)
    {
        this.delStr = delStr;
    }
    
    
}
