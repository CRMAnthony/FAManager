/*
 * 文 件 名:  SepicalVehiclesServiceImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ExcelUtil;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.dao.SpecialVehicleDao;
import com.jszt.domain.DimViolationSpecialVehicle;
import com.jszt.service.CommonService;
import com.jszt.service.SpecialVehicleService;
import com.jszt.vo.SpecialVehicleReq;
import com.jszt.vo.ViolationSpecialVehicle;

/**
 * 特殊车辆信息数据 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SpecialVehiclesServiceImpl implements SpecialVehicleService
{

	/**
	 * 有效字符
	 */
	private static final String valid = "有效";

	/**
	 * 无效字符
	 */
	private static final String inVain = "无效";

	/**
	 * 白名单
	 */
	private static final String whiteList = "白名单";

	/**
	 * 黑名单
	 */
	private static final String blackList = "黑名单";

	/**
	 * 大型车
	 */
	private static final String vehicle = "大型车";

	/**
	 * 小型车
	 */
	private static final String landaulet = "小型车";

	/**
	 * 货车
	 */
	private static final String trunk = "货车";

	/**
	 * 危险品车辆
	 */
	private static final String dangerousGoodsVehicles = "危险品车辆";

	/**
	 * 黄标车
	 */
	private static final String yellowLabelCar = "黄标车";

	/**
	 * 根据时间查询特殊车辆信息
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public List<ViolationSpecialVehicle> getSpecialVehicle(SpecialVehicleReq req)
	{
		List<DimViolationSpecialVehicle> dbList = ServiceUtil.getService(SpecialVehicleDao.class).getSpecialVehicleByTime(req);
		List<ViolationSpecialVehicle> voList = null;
		if (CollectionUtils.isNotEmpty(dbList))
		{
			voList = packageVoList(dbList);
		}
		return voList;
	}

	/**
	 * 新增特殊车辆信息
	 * 
	 * @param req
	 */
	@Override
	public void addSpecialVehicle(SpecialVehicleReq req)
	{
		List<DimViolationSpecialVehicle> dbList = ServiceUtil.getService(SpecialVehicleDao.class).getSpecialVehicle();

		for (DimViolationSpecialVehicle dimVehicles : dbList)
		{
			if (!req.getLicensePlate().equals(dimVehicles.getLicensePlate()))
			{
				continue;
			} else
			{
				if (req.getType().equals(dimVehicles.getType()))
				{
					continue;
				} else
				{
					Boolean status = (req.getEndDate() < dimVehicles.getStartDate() ||req.getStartDate() > dimVehicles.getEndDate());
					if (status)
					{
						continue;
					} else
					{
						ServiceAssert.isTrue(status, ErrorCode.DB_ERROR, "录入黑白名单无效,请重新录入！！！");
					}
				}
			}
		}
		ServiceUtil.getService(SpecialVehicleDao.class).addSpecialVehicle(req);
		// 更新特殊车辆缓存数据
		ServiceUtil.getService(CommonService.class).updateSpecialVehicle();
	}

	/**
	 * 删除特殊车辆信息
	 * 
	 * @param req
	 */
	@Override
	public void deleteSpecialVehicle(ViolationSpecialVehicle req)
	{
		String[] id = req.getId().split(",");
		ServiceUtil.getService(SpecialVehicleDao.class).deleteSpecialVehicle(id);
		// 更新特殊车辆缓存数据
		ServiceUtil.getService(CommonService.class).updateSpecialVehicle();
	}

	/**
	 * 封装VO对象 <功能详细描述>
	 * 
	 * @param dbList
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private List<ViolationSpecialVehicle> packageVoList(List<DimViolationSpecialVehicle> dbList)
	{
		List<ViolationSpecialVehicle> voList = new ArrayList<ViolationSpecialVehicle>();
		for (DimViolationSpecialVehicle dimLists : dbList)
		{
			ViolationSpecialVehicle voVehicle = new ViolationSpecialVehicle();
			Integer nowDay = Integer.valueOf(DateUtils.getCurrentTime().substring(0, 8));
			// 判断当前特殊车辆是否有效
			if (dimLists.getEndDate() >= nowDay)
			{
				voVehicle.setStatus(valid);
			} else
			{
				voVehicle.setStatus(inVain);
			}
			voVehicle.setId(dimLists.getId());
			voVehicle.setType(dimLists.getType());
			if (dimLists.getType() == 0)
			{
				voVehicle.setTypeName(whiteList);
			} else
			{
				voVehicle.setTypeName(blackList);
			}
			voVehicle.setLicensePlate(dimLists.getLicensePlate());
			voVehicle.setPlateTypeId(dimLists.getPlateTypeId());
			if (dimLists.getPlateTypeId().equals("1"))
			{

				voVehicle.setPlateTypeName(vehicle);
			} else
			{
				voVehicle.setPlateTypeName(landaulet);
			}
			voVehicle.setForbidType(dimLists.getForbidType());
			if (dimLists.getForbidType().equals("1"))
			{
				voVehicle.setForbidTypeName(trunk);
			} else if (dimLists.getForbidType().equals("2"))
			{
				voVehicle.setForbidTypeName(dangerousGoodsVehicles);
			} else
			{
				voVehicle.setForbidTypeName(yellowLabelCar);
			}
			String startTime = String.valueOf(dimLists.getStartDate());
			String endTime = String.valueOf(dimLists.getEndDate());
			String newStartTime = startTime.substring(0, 4) + "年" + startTime.substring(4, 6) + "月" + startTime.substring(6, 8) + "日";
			String newEndTime = endTime.substring(0, 4) + "年" + endTime.substring(4, 6) + "月" + endTime.substring(6, 8) + "日";
			voVehicle.setStartDate(newStartTime);
			voVehicle.setEndDate(newEndTime);
			voVehicle.setOwnerName(dimLists.getOwnerName());
			voVehicle.setPhoneNumber(dimLists.getPhoneNumber());
			voList.add(voVehicle);
		}
		return voList;
	}

	/**
	 * 特殊车辆信息封装Excel
	 * 
	 * @param voList
	 * @return
	 */
	@Override
	public ByteArrayOutputStream buildSpecialVehicleExcelBody(List<ViolationSpecialVehicle> voList)
	{
		// 创建Excel
		HSSFWorkbook workBook = new HSSFWorkbook();

		// 构造sheet页
		HSSFSheet sheet = ExcelUtil.getSheet(workBook, "特殊车辆信息Excel");

		int rowIndex = 0;

		int columnIndex = 0;
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "类型");
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌号码");
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车辆种类");
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "重点车辆类型");
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "开始时间");
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "结束时间");
		ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "状态");
		if (CollectionUtils.isNotEmpty(voList))
		{
			rowIndex = 1;
			for (ViolationSpecialVehicle row : voList)
			{
				columnIndex = 0;
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getTypeName());
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getLicensePlate());
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getPlateTypeName());
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getForbidTypeName());
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getStartDate());
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getEndDate());
				ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, row.getStatus());
				rowIndex++;
			}

		}
		return ExcelUtil.getByteArrayOutputStream(workBook);
	}

}
