/*
 * 文 件 名:  UnstandardVehicleController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jsits.commons.collections.ListUtils;
import com.jsits.commons.util.Assert;
import com.jsits.commons.util.ExcelUtil;
import com.jsits.commons.util.JsonUtil;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jsits.commons.util.StringUtils;
import com.jszt.adapter.util.AdapterContains;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Page;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.boulevard.webservice.export.Excel2003Response;
import com.jszt.boulevard.webservice.imp.Excel2003;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.service.UnstandardVehicleService;
import com.jszt.vo.DeleLicensePlatesReq;
import com.jszt.vo.UnstandardVehicleReq;

/**
 * 黄标车
 * 
 * @author gxj
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UnstandardVehicleController
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(UnstandardVehicleController.class);
    
    /**
     * 车牌号正则
     */
    private static Pattern licensePlatePattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
    
    /**
     * 手机号正则
     */
    //private static Pattern phoneNumberPattern = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
    
    /**
     * 身份证号15正则
     */
    //private static Pattern idCardPattern15 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
    
    /**
     * 身份证号15正则
     */
    //private static Pattern idCardPattern18 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
    
   /* @Test
    public void test() throws JsztException
    {
        String ss = "01";
        
        Assert.isTrue(ss.length() <= 2, ErrorCode.PARAM_ERROR, "aaaa");
        Matcher matcher15 = idCardPattern15.matcher("370802940221002");
        Matcher matcher18 = idCardPattern18.matcher("14022219901029602X");
        System.out.println(matcher15.matches());
        System.out.println(matcher18.matches());
        Matcher matcher = phoneNumberPattern.matcher("18762676132");
        System.out.println(matcher.matches());
        
        Matcher matcher1 = licensePlatePattern.matcher("苏A12312");
        System.out.println(matcher1.matches());    
    }*/
    
    /**
     * 黄标车录入
     * 
     * @param vehicle 黄标车入参
     * @return
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/inputUnstandardVehicle.json")
    public Response<String> inputUnstandardVehicle(DimUnstandardVehicle vehicle)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [addUnstandardVehicle]. vehicle=" + StringUtils.toString(vehicle) + ".");
        }
        Response<String> result = new Response<String>();
        
        try
        {
            // 参数校验
            Assert.notNull(vehicle, ErrorCode.PARAM_ERROR, "vehicle can not be null");
            
            Assert.notEmpty(vehicle.getLicensePlate(), ErrorCode.PARAM_ERROR, "车牌号不能为空");
            Assert.notEmpty(vehicle.getPlateTypeId(), ErrorCode.PARAM_ERROR, "车牌种类不能为空");
            Assert.notEmpty(vehicle.getOwnerName(), ErrorCode.PARAM_ERROR, "车主姓名不能为空");
            Assert.notEmpty(vehicle.getAddress(), ErrorCode.PARAM_ERROR, "地址不能为空");
            Assert.notEmpty(vehicle.getPhoneNumber(), ErrorCode.PARAM_ERROR, "联系电话不能为空");
            Assert.notEmpty(vehicle.getIdCard(), ErrorCode.PARAM_ERROR, "身份证号不能为空");
            
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            List<String> licensePlateList = unstandardVehicleService.getLicensePlates();
            if(!licensePlateList.contains(vehicle.getLicensePlate()))
            {   
                  unstandardVehicleService.addUnstandardVehicle(vehicle);
                  result.setRetMessage("成功!");
            }
            else
            {
                 result.setRetMessage("车牌号有重复,请重新输入");
                    
            }
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("addUnstandardVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(-1);
            result.setRetMessage(e.getMessage());
            logger.error("addUnstandardVehicle fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [addUnstandardVehicle].");
        }
        
        return result;
    }
    
    /**
     * 查询黄标车
     * 
     * @param req
     * @return
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/getUnstandardVehicle.json")
    public Response<List<DimUnstandardVehicle>> getUnstandardVehicle(UnstandardVehicleReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getUnstandardVehicle]." + StringUtils.toString(req) + ".");
        }
        Response<List<DimUnstandardVehicle>> result = new Response<List<DimUnstandardVehicle>>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null");
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            List<DimUnstandardVehicle> unstandardVehicleList = unstandardVehicleService.getUnstandardVehicle(req);
           
         // 是否满足分页条件
            if (AdapterContains.NEGATIVE_ONE != req.getPageIndex() && AdapterContains.NEGATIVE_ONE != req.getPageCount())
            {
                Page page = new Page(req.getPageIndex(), req.getPageCount(), unstandardVehicleList.size());
                result.setPage(page);
                unstandardVehicleList = ListUtils.subList(unstandardVehicleList, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1) * req.getPageCount() - 1);
            }
            
            result.setResult(unstandardVehicleList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getUnstandardVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getUnstandardVehicle fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getUnstandardVehicle]");
        }
        return result;
    }
    
    /**
     * 删除黄标车
     * 
     * @param licensePlate
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/deleteUnstandardVehicleByLicensePlate.json")
    public Response<String> deleteUnstandardVehicle(DeleLicensePlatesReq req)
    {
        Response<String> result = new Response<String>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null");
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            unstandardVehicleService.deleteUnstandardVehicle(req);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("deleteUnstandardVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("deleteUnstandardVehicle fail!", e);
        }
        
        return result;
    }
    
    /**
     * 根據車牌號更新黄标车信息
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */                              
    @WebExporter("/Service/FaManager/updateUnstandardVehicle.json")
    public Response<String> updateUnstandardVehicle(DimUnstandardVehicle vehicle)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateUnstandardVehicle]. vehicle=" + StringUtils.toString(vehicle) + ".");
        }
        Response<String> result = new Response<String>(0, "成功");
        try
       {
            // 参数校验
            Assert.notNull(vehicle, ErrorCode.PARAM_ERROR, "vehicle can not be null");
            
            Assert.notEmpty(vehicle.getLicensePlate(), ErrorCode.PARAM_ERROR, "车牌号不能为空");
            Assert.notEmpty(vehicle.getPlateTypeId(), ErrorCode.PARAM_ERROR, "车牌种类不能为空");
            Assert.notEmpty(vehicle.getOwnerName(), ErrorCode.PARAM_ERROR, "车主姓名不能为空");
            Assert.notEmpty(vehicle.getAddress(), ErrorCode.PARAM_ERROR, "地址不能为空");
            Assert.notEmpty(vehicle.getPhoneNumber(), ErrorCode.PARAM_ERROR, "联系电话不能为空");
            Assert.notEmpty(vehicle.getIdCard(), ErrorCode.PARAM_ERROR, "身份证号不能为空");
            
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            unstandardVehicleService.updateUnstandardVehicle(vehicle);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateUnstandardVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(e.getResultCode());
            result.setRetMessage(e.getMessage());
            logger.error("updateUnstandardVehicle fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [updateUnstandardVehicle].");
        }
        
        return result;
    }
    
    /**
     * 导出黄标车.excel
     */
    @WebExporter("/Service/FaManager/unstandardVehicleExcel.export")
    public Excel2003Response buildVioResultExcel(UnstandardVehicleReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [unstandardVehicleExcel].");
        }
        
        Excel2003Response response = new Excel2003Response();
        response.setFileName("导出黄标车excel");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "入参对象不能 为空!");
            // 业务处理
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            List<DimUnstandardVehicle> retList = unstandardVehicleService.getUnstandardVehicle(req);
            
            // 结果封装
            response.setByteArrayOutputStream(unstandardVehicleService.buildUnstandardVehicleExcelBody(retList));
        }
        catch (JsztException e)
        {
            logger.error("unstandardVehicleExcel fail!", e);
        }
        catch (Exception e)
        {
            logger.error("unstandardVehicleExcel fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [unstandardVehicleExcel]");
        }
        return response;
    }
    
    /**
     * 导入黄标车.excel
     * @param <Excel2003>
     */
    @WebExporter("/Service/FaManager/importUnstandardVehicleExcel.import")
    public Response<String> importUnstandardVehicleExcel(Excel2003 excel2003)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [importUnstandardVehicleExcel].");
        }
        
        Response<String> resp = new Response<String>(0, "成功");
        
        try
        {
            HSSFWorkbook workBook = excel2003.getWorkbook();
            HSSFSheet sheet = ExcelUtil.getSheet(workBook, "黄标车");

            String sheetName = workBook.getSheetName(0);
            Assert.isTrue(sheetName.equals("渣土车"), ErrorCode.PARAM_ERROR, "工作簿的工作表不是渣土车");
            
            Assert.notNull(sheet, ErrorCode.PARAM_ERROR, "工作簿的工作表不能为空");
            int rowNum = sheet.getLastRowNum();
            
            List<DimUnstandardVehicle> retList = new ArrayList<DimUnstandardVehicle>();
            List<String> licensePlateList = new ArrayList<String>();
            
            for (int i = 2; i <= rowNum; i++)
            {
                HSSFRow row = ExcelUtil.getRow(sheet, i);
                
                DimUnstandardVehicle unstandardVehicle = new DimUnstandardVehicle();
                
                String licensePlate = ExcelUtil.getCell(row, 0).getStringCellValue();
                checkLicensePlate(licensePlate, i + 1);
                unstandardVehicle.setLicensePlate(licensePlate);
                
                if (licensePlateList.contains(licensePlate))
                {
                    findLicensePlate(licensePlateList,licensePlate, i + 1);
                }
                
                licensePlateList.add(licensePlate);
                
                String plateTypeId = ExcelUtil.getCell(row, 1).getStringCellValue();
                checkPlateTypeId(plateTypeId, i + 1);
                unstandardVehicle.setPlateTypeId(plateTypeId);
                
                unstandardVehicle.setVehicleBrand(ExcelUtil.getCell(row, 2).getStringCellValue());
                
                String ownerName = ExcelUtil.getCell(row, 3).getStringCellValue();
                checkOwnerName(ownerName, i + 1);
                unstandardVehicle.setOwnerName(ownerName);
                
                String address = ExcelUtil.getCell(row, 4).getStringCellValue();
                //checkAddress(address, i + 1);
                unstandardVehicle.setAddress(address);
                
                String phoneNumber = ExcelUtil.getCell(row, 5).getStringCellValue();
                //checkPhoneNumber(phoneNumber, i + 1);
                unstandardVehicle.setPhoneNumber(phoneNumber);
                
                String idCard = ExcelUtil.getCell(row, 6).getStringCellValue();
                //checkIdCard(idCard, i + 1);
                unstandardVehicle.setIdCard(idCard);
                
                unstandardVehicle.setDescription(ExcelUtil.getCell(row,7).getStringCellValue());
                retList.add(unstandardVehicle);
                
            }
            
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            
            if(CollectionUtils.isNotEmpty(retList) && retList.size() > 0)
            {
                 unstandardVehicleService.dealUnstandardVehicle(retList);
            }
            // 结果封装
            resp.setResult(JsonUtil.obj2Json(retList));
        }
        catch (JsztException e)
        {
            resp.setRetCode(ErrorCode.UNKNOWN_ERROR);
            resp.setRetMessage(e.getMessage());
            logger.error("importUnstandardVehicleExcel fail!", e);
        }
        catch (Exception e)
        {
            resp.setRetCode(ErrorCode.UNKNOWN_ERROR);
            resp.setRetMessage(e.getMessage());
            logger.error("importUnstandardVehicleExcel fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [importUnstandardVehicleExcel] resp :" + StringUtils.toString(resp));
        }
        
        return resp;
    }
    
    /** 
     * 查找list中是否已存在相同的车牌号
     * @param licensePlateList
     * @param licensePlate
     * @see [类、类#方法、类#成员]
     */
    private void findLicensePlate(List<String> licensePlateList, String licensePlate,int rownum)
    {
        for (int i = 0; i < licensePlateList.size() ; i++)
        {
            ServiceAssert.isTrue(!licensePlateList.get(i).equals(licensePlate), -1, "当前行为第" + rownum + "行与第" + (i+3) + "行车牌号相同，请重新输入");
        }
    }

    /** 
     * 校验是否是正确格式的车牌号
     * @param licensePlate
     * @throws JsztException 
     * @see [类、类#方法、类#成员]
     */
    private void checkLicensePlate(String licensePlate, int rowNum) throws JsztException
    {
        Assert.notEmpty(licensePlate, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 车牌号不能为空!请重新输入");
        Matcher matcher = licensePlatePattern.matcher(licensePlate);
        Assert.isTrue(matcher.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 车牌号码非法!");
    }
    
    /** 
     * 校验车牌种类的长度不能超过2
     * @param plateTypeId
     * @param i
     * @throws JsztException 
     * @see [类、类#方法、类#成员]
     */
    private void checkPlateTypeId(String plateTypeId, int rowNum) throws JsztException
    {
        Assert.notEmpty(plateTypeId, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 车牌种类不能为空!请重新输入");
        Assert.isTrue(plateTypeId.length() <= 2, ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 车牌种类长度不能超过两位!");
    }
    
    /** 
     * 校验车主姓名不能为空
     * @param ownerName
     * @param i
     * @throws JsztException 
     * @see [类、类#方法、类#成员]
     */
    private void checkOwnerName(String ownerName, int rowNum) throws JsztException
    {
        Assert.notEmpty(ownerName, ErrorCode.PARAM_ERROR, "第" + rowNum + "行,车主姓名不能为空");
    }

    /** 
     * 校验地址不能为空
     * @param address
     * @param i
     * @throws JsztException 
     * @see [类、类#方法、类#成员]
     */
    /*private void checkAddress(String address, int rowNum) throws JsztException
    {
        Assert.notEmpty(address, ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 地址不能为空!");
    }*/
    
    /** 
     * 校验是否是手机号
     * @param phoneNumber
     * @param i
     * @throws JsztException 
     * @see [类、类#方法、类#成员]
     */
    /*private void checkPhoneNumber(String phoneNumber, int rowNum) throws JsztException
    {
        Assert.notEmpty(phoneNumber, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 手机号不能为空!请重新输入");
        Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
        Assert.isTrue(matcher.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 手机号码非法!");
    }*/
    

    /** 
     * 校验是否是身份证号
     * @param idCard
     * @param i
     * @throws JsztException 
     * @see [类、类#方法、类#成员]
     */
    /*private void checkIdCard(String idCard, int rowNum) throws JsztException
    {
        Assert.notEmpty(idCard, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 身份证号不能为空!请重新输入");
        Matcher matcher15 = idCardPattern15.matcher(idCard);
        Matcher matcher18 = idCardPattern18.matcher(idCard);
        Assert.isTrue(matcher15.matches()||matcher18.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 身份证号非法!");
    }*/
}
