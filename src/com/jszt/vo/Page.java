package com.jszt.vo;

/**
 * 分页实体
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Page
{
    /**
     * 当前页数
     */
    private int currentPage;
    
    /**
     * 每页记录数
     */
    private int pageRecords;
    
    /**
     * 总记录数
     */
    private int totalRecords;

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public int getPageRecords()
    {
        return pageRecords;
    }

    public void setPageRecords(int pageRecords)
    {
        this.pageRecords = pageRecords;
    }

    public int getTotalRecords()
    {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords)
    {
        this.totalRecords = totalRecords;
    }
    
    
}
