package com.sys.comm.page;

import java.util.List;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sys.comm.constants.Constants;
import com.sys.comm.util.ServletUtils;
import com.sys.comm.util.StringUtils;

/**
 * 表格数据处理
 *
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            PageHelper.startPage(pageNum, pageSize);
        }
    }
    /**
     * 响应请求分页数据
     */
    public static <R> TableDataInfo<R> getDataTable(List<R> list)
    {

    	TableDataInfo<R> rspData = new TableDataInfo<R>();
    	PageInfo<R> page=new PageInfo<R>(list);
    	rspData.setPageNum(page.getPageNum());
        rspData.setPageSize(page.getPageSize());
        rspData.setRows(list);
        rspData.setTotal(page.getTotal());
        return rspData;
    }
    
    /**
     * 响应请求分页数据
     */
    public static <M> TableDataInfo<M> getDataTable(List<M> reslist,long l,Integer pageNum,Integer pageSize)
    {

    	TableDataInfo<M> rspData = new TableDataInfo<M>();
    	rspData.setPageNum(pageNum);
        rspData.setPageSize(pageSize);
        rspData.setRows(reslist);
        rspData.setTotal(l);
        return rspData;
    }
}
