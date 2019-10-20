package com.sys.comm.page;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表格分页数据对象
 * 
 * @author whl
 */
/*@ApiModel("分页返回数据")*/
@Data
public class TableDataInfo<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 总记录数 */
    @ApiModelProperty("总记录数")
    private long total;
    @ApiModelProperty("当前页数")
    private Integer pageNum;
    /** 每页显示记录数 */
    @ApiModelProperty("每页显示记录数 ")
    private Integer pageSize;
    /** 列表数据 */
    private List<T> rows;

  
}
