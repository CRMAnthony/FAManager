/*
 * 文 件 名:  ConstructionVehicleServiceImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.jszt.dao.DimConstructionVehicleDao;
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.service.ConstructionVehicleService;
import com.jszt.vo.ConstructionVehicleReq;

/**
 * 渣土车业务接口实现
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConstructionVehicleServiceImpl implements ConstructionVehicleService
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(ConstructionVehicleServiceImpl.class);
    
    /**
     * 新增渣土车信息
     * @param dimConstructionVehicle
     */
    @Override
    public void addConstructionVehicle(DimConstructionVehicle dimConstructionVehicle)
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        constructionVehicleDao.addConstructionVehicle(dimConstructionVehicle);
        
        /*CommonService commonService = ServiceUtil.getService(CommonService.class);
        commonService.updateUnstandardVehicle();*/
    }
    
    /**
     * 根据车牌号、使用人、手机号查询渣土车信息列表
     * @param req
     * @return
     */
    @Override
    public List<DimConstructionVehicle> getConstructionVehicle(ConstructionVehicleReq req)
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        return constructionVehicleDao.getConstructionVehicle(req);
    }
    
    /**
     * 根据车牌号删除渣土车信息，支持多删除
     * @param req
     */
    @Override
    public void deleteConstructionVehicle(ConstructionVehicleReq req)
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        constructionVehicleDao.deleteConstructionVehicle(req.getLicensePlates());
        
        /*CommonService commonService = ServiceUtil.getService(CommonService.class);
        commonService.updateUnstandardVehicle();*/
    }
    
    /**
     * 渣土车Excel封装
     * @param retList
     * @return
     */
    @Override
    public ByteArrayOutputStream buildConstructionVehicleExcelBody(List<DimConstructionVehicle> retList)
    {
        // 创建EXCEL
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 构造sheet页
        HSSFSheet sheet = ExcelUtil.getSheet(workbook, "渣土车基本信息");
        
        int rowIndex = 0;
        int columnIndex = 0;
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌种类");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车辆品牌");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "身份证号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "使用人");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "联系电话");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "地址");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "登记日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "开始日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "结束日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通行时段");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "登记老板");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "描述");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "公司名称");
        
        if (CollectionUtils.isNotEmpty(retList))
        {
            rowIndex = 1;
            
            for (DimConstructionVehicle res : retList)
            {
                columnIndex = 0;
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getLicensePlate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getPlateTypeId()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getVehicleBrand());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getIdCard()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getOwnerName());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getPhoneNumber()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getAddress());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getRegisterDate()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getBeginDate()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, String.valueOf(res.getEndDate()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getPassPeriod());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getRegisterPeople());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getDescription());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getCompanyName());
                rowIndex++;
            }
        }
        return ExcelUtil.getByteArrayOutputStream(workbook);
    }
    
    /**
     * 根据车牌号修改渣土车信息
     * @param vehicle
     */
    @Override
    public void updateConstructionVehicle(DimConstructionVehicle vehicle)
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        constructionVehicleDao.updateConstructionVehicle(vehicle);
        
        /*CommonService commonService = ServiceUtil.getService(CommonService.class);
        commonService.updateUnstandardVehicle();*/
    }
    
    /**
     * 
     * @param retList
     */
    @Override
    public void dealConstructionVehicle(List<DimConstructionVehicle> retList)
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        // 获取数据源工厂
        ConnectionFactory factory = ServiceUtil.getService("constructionConFactory");
        Connection conn = null;
        try
        {
            conn = factory.getConnection();
            conn.setAutoCommit(false);
        
            constructionVehicleDao.deleteConstructionVehicle(conn);
            constructionVehicleDao.addBatchConstructionVehicle(conn, retList);
            DbUtil.commitTransaction(conn);
        }
        catch (SQLException e)
        {
            DbUtil.rollBackTransaction(conn);
            logger.error("dealConstructionVehicle fail!" + ExceptionUtils.getStackTrace(e));
            DbUtil.rollBackTransaction(conn);
            throw new ServiceException(e);
        }
        finally
        {
            CloseUtils.close(conn);
        }
    }
    
    /**
     * 查询所有车牌号
     * @return
     */
    @Override
    public List<String> getLicensePlates()
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        return constructionVehicleDao.getLicensePlates();
    }

    /**
     * 根据公司名稱查詢屬於該公司的渣土车
     * @param vehicleReq
     */
    @Override
    public List<DimConstructionVehicle> getConsVehicleByCompanyName(ConstructionVehicleReq vehicleReq)
    {
        DimConstructionVehicleDao constructionVehicleDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        return constructionVehicleDao.getConsVehicleByCompanyName(vehicleReq);
    }

    /**
     * 查詢所有公司(顯示公司名稱)
     * @return
     */
    @Override
    public List<DimConstructionVehicle> getCompanyNames()
    {
        List<DimConstructionVehicle> list = new ArrayList<DimConstructionVehicle>();
        List<String> dblist = ServiceUtil.getService(DimConstructionVehicleDao.class).getCompanyNames();
        
        if(CollectionUtils.isNotEmpty(dblist))
        {
            for(String companyName : dblist)
            {
                DimConstructionVehicle dimConstructionVehicle = new DimConstructionVehicle();
                dimConstructionVehicle.setCompanyName(companyName);
                list.add(dimConstructionVehicle);
            }
        }
        
        return list;
    }
}
