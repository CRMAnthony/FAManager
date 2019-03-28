/*
 * 文 件 名:  ConstructionVehicleController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
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
import org.junit.Test;

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
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.service.ConstructionVehicleService;
import com.jszt.vo.ConstructionVehicleReq;

/**
 * 渣土车
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConstructionVehicleController
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(ConstructionVehicleController.class);
    
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
    
    /**
     * 8位日期正则
     */
    private static Pattern datePattern = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)");
    
    @Test
    public void test() throws JsztException
    {
        String ss = "01";
        
        Assert.isTrue(ss.length() <= 2, ErrorCode.PARAM_ERROR, "aaaa");
        /*Matcher matcher15 = idCardPattern15.matcher("370802940221002");
        Matcher matcher18 = idCardPattern18.matcher("14022219901029602X");
        System.out.println(matcher15.matches());
        System.out.println(matcher18.matches());
        Matcher matcher = phoneNumberPattern.matcher("18762676132");
        System.out.println(matcher.matches());
        
        Matcher matcher1 = licensePlatePattern.matcher("苏A12312");
        System.out.println(matcher1.matches());*/ 
        
        Matcher matcher = datePattern.matcher("20160230");
        System.out.println(matcher.matches());
    }
    
    /**
     * 新增渣土车信息
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/addConstructionVehicle.json")
    public Response<String> addConstructionVehicle(DimConstructionVehicle vehicle)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [addConstructionVehicle]. vehicle=" + StringUtils.toString(vehicle) + ".");
        }
        Response<String> result = new Response<String>();
        
        try
        {
            checkParam(vehicle);
            
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<String> licensePlateList = constructionVehicleService.getLicensePlates();
            if(!licensePlateList.contains(vehicle.getLicensePlate()))
            {   
                constructionVehicleService.addConstructionVehicle(vehicle);
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
            logger.error("addConstructionVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(-1);
            result.setRetMessage(e.getMessage());
            logger.error("addConstructionVehicle fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [addConstructionVehicle].");
        }
        
        return result;
    }

    /**
     * 根据车牌号、使用人、手机号查询渣土车信息列表
     * 
     * @param req
     * @return
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/getConstructionVehicle.json")
    public Response<List<DimConstructionVehicle>> getConstructionVehicle(ConstructionVehicleReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getConstructionVehicle]." + StringUtils.toString(req) + ".");
        }
        Response<List<DimConstructionVehicle>> result = new Response<List<DimConstructionVehicle>>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null");
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<DimConstructionVehicle> constructionVehicleList = constructionVehicleService.getConstructionVehicle(req);
           
            // 是否满足分页条件
            if (AdapterContains.NEGATIVE_ONE != req.getPageIndex() && AdapterContains.NEGATIVE_ONE != req.getPageCount())
            {
                Page page = new Page(req.getPageIndex(), req.getPageCount(), constructionVehicleList.size());
                result.setPage(page);
                constructionVehicleList = ListUtils.subList(constructionVehicleList, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1) * req.getPageCount() - 1);
            }
            
            result.setResult(constructionVehicleList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getConstructionVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getConstructionVehicle fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getConstructionVehicle]");
        }
        return result;
    }
    
    /**
     * 根据车牌号删除渣土车
     * 
     * @param licensePlate
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/deleteConstructionVehicleByLicensePlate.json")
    public Response<String> deleteConstructionVehicle(ConstructionVehicleReq req)
    {
        Response<String> result = new Response<String>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null");
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            constructionVehicleService.deleteConstructionVehicle(req);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("deleteConstructionVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("deleteConstructionVehicle fail!", e);
        }
        
        return result;
    }
    
    /**
     * 根据车牌号修改渣土车
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */                              
    @WebExporter("/Service/FaManager/updateConstructionVehicle.json")
    public Response<String> updateConstructionVehicle(DimConstructionVehicle vehicle)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateConstructionVehicle]. vehicle=" + StringUtils.toString(vehicle) + ".");
        }
        
        Response<String> result = new Response<String>(0, "成功");
        
        try
       {
            //参数校验
            checkParam(vehicle);
            
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            constructionVehicleService.updateConstructionVehicle(vehicle);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateConstructionVehicle fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(e.getResultCode());
            result.setRetMessage(e.getMessage());
            logger.error("updateConstructionVehicle fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [updateConstructionVehicle].");
        }
        
        return result;
    }
    
    /**
     * 根据公司名稱查詢屬於該公司的渣土车
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */                              
    @WebExporter("/Service/FaManager/getConsVehicleByCompanyName.json")
    public Response<List<DimConstructionVehicle>> getConstructionVehicleByCompanyName(ConstructionVehicleReq vehicleReq)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getConsVehicleByCompanyName]. vehicleReq=" + StringUtils.toString(vehicleReq) + ".");
        }
        
        Response<List<DimConstructionVehicle>> result = new Response<List<DimConstructionVehicle>>(0, "成功");
        
        try
       {
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<DimConstructionVehicle> dblist = constructionVehicleService.getConsVehicleByCompanyName(vehicleReq);
            
            result.setResult(dblist);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getConsVehicleByCompanyName fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getConsVehicleByCompanyName].");
        }
        
        return result;
    }
    
    /**
     * 查詢所有公司(顯示公司名稱)
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */                              
    @WebExporter("/Service/FaManager/getCompanyNames.json")
    public Response<List<DimConstructionVehicle>> getCompanyNames()
    {
        
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getCompanyNames]. ");
        }
        
        Response<List<DimConstructionVehicle>> result = new Response<List<DimConstructionVehicle>>(0, "成功");
        
        try
        {
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<DimConstructionVehicle> dblist = constructionVehicleService.getCompanyNames();
            
            result.setResult(dblist);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getCompanyNames fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getCompanyNames].");
        }
        
        return result;
    }
    
    /**
     * 导出渣土车.excel
     */
    @WebExporter("/Service/FaManager/constructionVehicleExcel.export")
    public Excel2003Response buildConstructionVehicleResultExcel(ConstructionVehicleReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [constructionVehicleExcel].");
        }
        
        Excel2003Response response = new Excel2003Response();
        response.setFileName("导出渣土车excel");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "入参对象不能 为空!");
            // 业务处理
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<DimConstructionVehicle> retList = constructionVehicleService.getConstructionVehicle(req);
            
            // 结果封装
            response.setByteArrayOutputStream(constructionVehicleService.buildConstructionVehicleExcelBody(retList));
        }
        catch (JsztException e)
        {
            logger.error("constructionVehicleExcel fail!", e);
        }
        catch (Exception e)
        {
            logger.error("constructionVehicleExcel fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [constructionVehicleExcel]");
        }
        return response;
    }
    
    /**
     * 导入渣土车.excel
     * @param <Excel2003>
     */
    @WebExporter("/Service/FaManager/importConstructionVehicleExcel.import")
    public Response<String> importConstructionVehicleExcel(Excel2003 excel2003)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [importConstructionVehicleExcel].");
        }
        
        Response<String> resp = new Response<String>(0, "成功");
        
        try
        {
            HSSFWorkbook workBook = excel2003.getWorkbook();
            String sheetName = workBook.getSheetName(0);
            HSSFSheet sheet = ExcelUtil.getSheet(workBook, "渣土车");
            Assert.isTrue(sheetName.equals("渣土车"), ErrorCode.PARAM_ERROR, "工作簿的工作表不是渣土车");
            Assert.notNull(sheet, ErrorCode.PARAM_ERROR, "工作簿的工作表不能为空");
            int rowNum = sheet.getLastRowNum();
            
            List<DimConstructionVehicle> retList = new ArrayList<DimConstructionVehicle>();
            List<String> licensePlateList = new ArrayList<String>();
            
            for (int i = 2; i <= rowNum; i++)
            {
                HSSFRow row = ExcelUtil.getRow(sheet, i);
                
                DimConstructionVehicle constructionVehicle = new DimConstructionVehicle();
                
                String licensePlate = ExcelUtil.getCell(row, 0).getStringCellValue();
                checkLicensePlate(licensePlate, i + 1);
                constructionVehicle.setLicensePlate(licensePlate);
                
                if (licensePlateList.contains(licensePlate))
                {
                    findLicensePlate(licensePlateList,licensePlate, i + 1);
                }
                
                licensePlateList.add(licensePlate);
                
                String plateTypeId = ExcelUtil.getCell(row, 1).getStringCellValue();
                checkPlateTypeId(plateTypeId, i + 1);
                constructionVehicle.setPlateTypeId(plateTypeId);
                
                constructionVehicle.setVehicleBrand(ExcelUtil.getCell(row, 2).getStringCellValue());
                
                String idCard = ExcelUtil.getCell(row, 3).getStringCellValue();
                checkIdCard(idCard, i + 1);
                constructionVehicle.setIdCard(idCard);
                
                String ownerName = ExcelUtil.getCell(row, 4).getStringCellValue();
                checkOwnerName(ownerName, i + 1);
                constructionVehicle.setOwnerName(ownerName);
                
                String phoneNumber = ExcelUtil.getCell(row, 5).getStringCellValue();
                checkPhoneNumber(phoneNumber, i + 1);
                constructionVehicle.setPhoneNumber(phoneNumber);
                
                String address = ExcelUtil.getCell(row, 6).getStringCellValue();
                checkAddress(address, i + 1);
                constructionVehicle.setAddress(address);
                
                String registerDate = ExcelUtil.getCell(row, 7).getStringCellValue();
                checkRegisterDate(registerDate, i + 1);
                constructionVehicle.setRegisterDate(Integer.valueOf(registerDate));
                
                String beginDate = ExcelUtil.getCell(row, 8).getStringCellValue();
                checkBeginDate(beginDate, i + 1);
                constructionVehicle.setBeginDate(Integer.valueOf(beginDate));
                
                String endDate = ExcelUtil.getCell(row, 9).getStringCellValue();
                checkEndDate(endDate, i + 1);
                constructionVehicle.setEndDate(Integer.valueOf(endDate));
                
                String passPeriod = ExcelUtil.getCell(row, 10).getStringCellValue();
                checkPassPeriod(passPeriod, i + 1);
                constructionVehicle.setPassPeriod(passPeriod);
                
                String registerPeople = ExcelUtil.getCell(row, 11).getStringCellValue();
                checkRegisterPeople(registerPeople, i + 1);
                constructionVehicle.setRegisterPeople(registerPeople);
                
                constructionVehicle.setDescription(ExcelUtil.getCell(row,12).getStringCellValue());
                constructionVehicle.setCompanyName(ExcelUtil.getCell(row,13).getStringCellValue());
                
                retList.add(constructionVehicle);
                
            }
            
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            
            if(CollectionUtils.isNotEmpty(retList) && retList.size() > 0)
            {
                constructionVehicleService.dealConstructionVehicle(retList);
            }
            // 结果封装
            resp.setResult(JsonUtil.obj2Json(retList));
        }
        catch (JsztException e)
        {
            resp.setRetCode(ErrorCode.UNKNOWN_ERROR);
            resp.setRetMessage(e.getMessage());
            logger.error("importConstructionVehicleExcel fail!", e);
        }
        catch (Exception e)
        {
            resp.setRetCode(ErrorCode.UNKNOWN_ERROR);
            resp.setRetMessage(e.getMessage());
            logger.error("importConstructionVehicleExcel fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [importConstructionVehicleExcel] resp :" + StringUtils.toString(resp));
        }
        
        return resp;
    }
    
    //参数校验
    private void checkParam(DimConstructionVehicle vehicle)
        throws JsztException
    {
        // 参数校验
        Assert.notNull(vehicle, ErrorCode.PARAM_ERROR, "vehicle can not be null");
        
        Assert.notEmpty(vehicle.getLicensePlate(), ErrorCode.PARAM_ERROR, "车牌号不能为空");
        Assert.notEmpty(vehicle.getPlateTypeId(), ErrorCode.PARAM_ERROR, "车牌种类不能为空");
        Assert.notEmpty(vehicle.getOwnerName(), ErrorCode.PARAM_ERROR, "使用人不能为空");
        
        int RegisterDate = vehicle.getRegisterDate();
        Assert.isTrue(RegisterDate != 0, ErrorCode.PARAM_ERROR, "登记日期不能为空");
        
        int beginDate = vehicle.getBeginDate();
        Assert.isTrue(beginDate != 0, ErrorCode.PARAM_ERROR, "开始日期不能为空");
        
        int endDate = vehicle.getEndDate();
        Assert.isTrue(endDate != 0, ErrorCode.PARAM_ERROR, "结束日期不能为空");
        
        Assert.notEmpty(vehicle.getPassPeriod(), ErrorCode.PARAM_ERROR, "通行时段不能为空");
        
        Assert.notEmpty(vehicle.getRegisterPeople(), ErrorCode.PARAM_ERROR, "登记老板不能为空");
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
     * 验证登记日期不能为空，且为正确的日期
     * 
     * @param registerDate
     * @param i
     * @see [类、类#方法、类#成员]
     */
    private void checkRegisterDate(String registerDate, int rowNum) throws JsztException
    {
        Assert.notEmpty(registerDate, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 登记日期不能为空!请重新输入");
        Matcher matcher = datePattern.matcher(registerDate);
        Assert.isTrue(matcher.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 登记日期非法!");
    }
    
    /**
     * 验证开始时间不能为空，且为正确的日期
     * 
     * @param beginDate
     * @param i
     * @see [类、类#方法、类#成员]
     */
    private void checkBeginDate(String beginDate, int rowNum) throws JsztException
    {
        Assert.notEmpty(beginDate, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 开始日期不能为空!请重新输入");
        Matcher matcher = datePattern.matcher(beginDate);
        Assert.isTrue(matcher.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 开始日期非法!");
    }
    
    /**
     * 验证结束时间不能为空，且为正确的日期
     * 
     * @param endDate
     * @param i
     * @see [类、类#方法、类#成员]
     */
    private void checkEndDate(String endDate, int rowNum) throws JsztException
    {
        Assert.notEmpty(endDate, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 结束日期不能为空!请重新输入");
        Matcher matcher = datePattern.matcher(endDate);
        Assert.isTrue(matcher.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 结束日期非法!");
    }
    
    /**
     * 验证通行时段不能为空
     * 
     * @param passPeriod
     * @param i
     * @see [类、类#方法、类#成员]
     */
    private void checkPassPeriod(String passPeriod, int rowNum) throws JsztException
    {
        Assert.notEmpty(passPeriod, ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 通行时段不能为空!");
    }

    /**
     * 校验登记老板不能为空
     * 
     * @param registerPeople
     * @param i
     * @see [类、类#方法、类#成员]
     */
    private void checkRegisterPeople(String registerPeople, int rowNum) throws JsztException
    {
        Assert.notEmpty(registerPeople, ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 登记老板不能为空!");
    }
    
    /**
     * 校验地址不能为空
     * 
     * @param address
     * @param rowNum
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    private void checkAddress(String address, int rowNum) throws JsztException
    {
        Assert.notEmpty(address, ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 地址不能为空!");
    }
    
    /**
     * 校验身份证号不能为空
     * 
     * @param idCard
     * @param rowNum
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    private void checkIdCard(String idCard, int rowNum) throws JsztException
    {
        Assert.notEmpty(idCard, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 身份证号不能为空!请重新输入");
        //Matcher matcher15 = idCardPattern15.matcher(idCard);
        //Matcher matcher18 = idCardPattern18.matcher(idCard);
        //Assert.isTrue(matcher15.matches()||matcher18.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 身份证号非法!");
    }
    
    /**
     * 验证手机号不能为空
     * 
     * @param phoneNumber
     * @param rowNum
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    private void checkPhoneNumber(String phoneNumber, int rowNum) throws JsztException
    {
        Assert.notEmpty(phoneNumber, ErrorCode.PARAM_ERROR, "导入失败,excel文件第" + rowNum + "行, 手机号不能为空!请重新输入");
        //Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
        //Assert.isTrue(matcher.matches(), ErrorCode.PARAM_ERROR, "第" + rowNum + "行, 手机号码非法!");
    }
}
