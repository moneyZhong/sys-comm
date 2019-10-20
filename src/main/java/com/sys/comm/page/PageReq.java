package com.sys.comm.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "PageReq")
public class PageReq<T> {

   @NotNull(message = "pageNum不能为空")
    @ApiModelProperty(value = "当前页")
    private Integer pageNum=1;


    @NotNull(message = "pageSize不能为空")
    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize=10;

    @Valid
    @ApiModelProperty(value = "数据")
    private T data;

}
