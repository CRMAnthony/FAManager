/*
 * 文 件 名:  ConnectionFactorySpi.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  lvwenbin
 * 修改时间:  2013年10月15日
 * 修改内容:  <修改内容>
 */
package com.jszt.service.common.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.util.CloseUtils;

/**
 * 获取数据库连接的工厂实现类
 * <功能详细描述>
 * 
 * @author  lvwenbin
 * @version  [版本号, 2013年10月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConnectionFactorySpi implements ConnectionFactory
{
    /**
     * 数据源 
     */
    private DataSource dataSource;
    
    /**
     * 备机数据源
     */
    private DataSource standbyDataSource;
    
    /**
     * 有效数据源
     */
    private volatile DataSource effectiveDataSource;
    
    /**
     * 双机热备开关
     */
    private boolean doubleMachineHotStandby; 
    
    /**
     * 连接有效性检验时间
     */
    private int validTimeOut = 5;
    
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(ConnectionFactorySpi.class);
    
    public void init()
    {
        this.effectiveDataSource = dataSource;
    }
    
    /**
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection()
        throws SQLException
    {
        Assert.notNull(effectiveDataSource);
        return effectiveDataSource.getConnection();
    }
    
    /**
     * 双机数据源守护
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void dataSourceGuardian()
    {
        if (doubleMachineHotStandby)
        {
            Assert.notNull(dataSource);
            Assert.notNull(standbyDataSource);
            
            Connection conn = null;
            
            try
            {
                conn = dataSource.getConnection();
                
                // 主数据源可达
                if (conn.isValid(validTimeOut))
                {
                    // 从备机切换主机
                    if (this.effectiveDataSource != dataSource)
                    {
                        this.effectiveDataSource = dataSource;
                        logger.info("Main dataSource connection back to normal!");
                    }
                }
                // 主数据源不可可达,启用备机数据源
                else
                {
                    switchToStandby();
                }
            }
            catch (SQLException e)
            {
                logger.error("getConnection fail!", e);
                switchToStandby();
            }
            catch (Exception e)
            {
                logger.error("getConnection fail!", e);
                switchToStandby();
            }
            finally
            {
                CloseUtils.close(conn);
            }
        }
    }

    private void switchToStandby()
    {
        this.effectiveDataSource = standbyDataSource;
        logger.warn("Main dataSource connection inaccessible, switch The standby!");
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setStandbyDataSource(DataSource standbyDataSource)
    {
        this.standbyDataSource = standbyDataSource;
    }

    public void setDoubleMachineHotStandby(boolean doubleMachineHotStandby)
    {
        this.doubleMachineHotStandby = doubleMachineHotStandby;
    }

    public void setValidTimeOut(int validTimeOut)
    {
        this.validTimeOut = validTimeOut;
    }
}
