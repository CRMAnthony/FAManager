package com.jszt.vo;

/**
 * 交叉口
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-6-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Intersection
{
    private String road1;
    private String road2;
    
    public  Intersection()
    {
        
    }
    
    public  Intersection(String road1, String road2)
    {
        this.road1 = road1;
        this.road2 = road2;
    }
    
    /**
     * 获取两个交叉口所在的同一道路
     * <一句话功能简述>
     * <功能详细描述>
     * @param intersection
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String getSameRoad(Intersection intersection)
    {
        String sameRoad = null;
        if (this.road1.equals(intersection.getRoad1())|| this.road1.equals(intersection.getRoad2()))
        {
            sameRoad = this.road1;
        }else if (this.road2.equals(intersection.getRoad1())|| this.road2.equals(intersection.getRoad2()))
        {
            sameRoad = this.road2;
        }
        return sameRoad;
    }
//    
//    public void replaceBySameRoad(String sameRoad)
//    {
//        if (this.road2.equals(sameRoad))
//        {
//            this.road2 = this.road1;
//            this.road1 = sameRoad;
//        }
//    }
//    public void setIntersection(Intersection intersection)
//    {
//        this.road1 = intersection.getRoad1();
//        this.road2 = intersection.getRoad2();
//    }
    
    public String getRoad1()
    {
        return road1;
    }
    public void setRoad1(String road1)
    {
        this.road1 = road1;
    }
    public String getRoad2()
    {
        return road2;
    }
    public void setRoad2(String road2)
    {
        this.road2 = road2;
    }

    @Override
    public String toString()
    {
        return "Intersection [road1=" + road1 + ", road2=" + road2 + "]";
    }
    
    
}
