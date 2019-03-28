/*
 * 文 件 名:  UnstandardVehicleServiceImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.util.CloseUtils;
import com.jsits.commons.util.DbUtil;
import com.jsits.commons.util.ExcelUtil;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimUnstandardVehicleDao;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.service.CommonService;
import com.jszt.service.UnstandardVehicleService;
import com.jszt.vo.DeleLicensePlatesReq;
import com.jszt.vo.UnstandardVehicleReq;

/**
 * 黄标车相关业务接口
 * @author  gxj
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnstandardVehicleServiceImpl implements UnstandardVehicleService
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(UnstandardVehicleServiceImpl.class);
    /**
     * 录入黄标车
     * @param dimUnstandardVehicle
     */
    @Override
    public void addUnstandardVehicle(DimUnstandardVehicle unstandardVehicle)
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        unstandardVehicleDao.addUnstandardVehicle(unstandardVehicle);
        
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        commonService.updateUnstandardVehicle();
    }

    /**
     * 查询黄标车
     * @param req
     * @return
     */
    @Override
    public List<DimUnstandardVehicle> getUnstandardVehicle(UnstandardVehicleReq req)
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        return unstandardVehicleDao.getUnstandardVehicle(req);
    }

    /**
     * 根据车牌号删除黄标车
     * @param licensePlate
     */
    @Override
    public void deleteUnstandardVehicle(DeleLicensePlatesReq req)
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        unstandardVehicleDao.deleteUnstandardVehicle(req.getLicensePlates());
        
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        commonService.updateUnstandardVehicle();
    }

    /**
     * 根據車牌號更新黄标车信息
     * @param vehicle
     */
    @Override
    public void updateUnstandardVehicle(DimUnstandardVehicle vehicle)
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        unstandardVehicleDao.updateUnstandardVehicle(vehicle);
        
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        commonService.updateUnstandardVehicle();
    }
    
    /**
     * 黄标车Excel封装
     * @param retList
     * @return
     */
    @Override
    public ByteArrayOutputStream buildUnstandardVehicleExcelBody(List<DimUnstandardVehicle> retList)
    {
        // 创建EXCEL
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 构造sheet页
        HSSFSheet sheet = ExcelUtil.getSheet(workbook, "黄标车基本信息");
        
        int rowIndex = 0;
        int columnIndex = 0;
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌种类");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车辆品牌");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车主姓名");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "地址");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "联系电话");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "身份证号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "描述");
        
        if (CollectionUtils.isNotEmpty(retList))
        {
            rowIndex = 1;
            
            for (DimUnstandardVehicle res : retList)
            {
                columnIndex = 0;
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getLicensePlate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getPlateTypeId()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getVehicleBrand());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getOwnerName());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getAddress());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getPhoneNumber()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getIdCard()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getDescription());
                
                rowIndex++;
            }
        }
        return ExcelUtil.getByteArrayOutputStream(workbook);
    }

    
   
    /**
     * @param retList
     */
    @Override
    public void dealUnstandardVehicle(List<DimUnstandardVehicle> retList)
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        // 获取数据源工厂
        ConnectionFactory factory = ServiceUtil.getService("unstandardConFactory");
        Connection conn = null;
        try
        {
            conn = factory.getConnection();
            conn.setAutoCommit(false);
        
            unstandardVehicleDao.deleteUnstandardVehicle(conn);
            unstandardVehicleDao.addBatchUnstandardVehicle(conn, retList);
            DbUtil.commitTransaction(conn);
        }
        catch (SQLException e)
        {
            DbUtil.rollBackTransaction(conn);
            logger.error("dealUnstandardVehicle fail!" + ExceptionUtils.getStackTrace(e));
            DbUtil.rollBackTransaction(conn);
            throw new ServiceException(e);
        }
        finally
        {
            CloseUtils.close(conn);
        }
    }

    /**
     * @return
     */
    @Override
    public List<String> getLicensePlates()
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        return unstandardVehicleDao.getLicensePlates();
    }
}
