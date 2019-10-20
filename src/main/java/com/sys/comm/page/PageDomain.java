package com.sys.comm.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页数据
 * 
 * @author wanghl
 */
@ApiModel("分页请求数据")
@Data
@EqualsAndHashCode(callSuper=false)
public class PageDomain
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -541804605530922190L;
	/** 当前记录起始索引 */
	@ApiModelProperty("当前页")
    private Integer pageNum=1;
    /** 每页显示记录数 */
	@ApiModelProperty("当前页数")
    private Integer pageSize=10;
 
}
