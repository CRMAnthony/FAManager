package com.jszt.vo;

/**
 * 更新通行证请求参数（审核、打印）
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdatePassCardReq
{
    /**
     * 通行证编号
     */
    private String cardId;
    
    /**
     * 通行证类型（0-临时，1-长期）
     */
    private int cardTypeId;
    
    /**
     * 审批意见
     */
    private String opinion;
    
    /**
     * 审批结果
     */
    private int result;
    
    /**
     * 是否打印（0-未打印，1-已打印）
     */
    private int print;

    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }

    public int getCardTypeId()
    {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId)
    {
        this.cardTypeId = cardTypeId;
    }

    public String getOpinion()
    {
        return opinion;
    }

    public void setOpinion(String opinion)
    {
        this.opinion = opinion;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public int getPrint()
    {
        return print;
    }

    public void setPrint(int print)
    {
        this.print = print;
    }
    
    
}
