package com.sf.gis.cds.common.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Created by 01115468 on 2018/2/3.
 */
@ApiModel
@Data
public class BaseReq {

    @ApiModelProperty(hidden = true)
    public final int DEF_PAGESIZE = 10;

    @ApiModelProperty(hidden = true)
    public final int PAGE_START = 1;

    /*表示不分页*/
    @ApiModelProperty(hidden = true)
    public static final String NOPAGING = "0";

    /*默认分页*/
    @ApiModelProperty(hidden = true)
    public static final String DEF_PAGING = "1";
    /**
     * 1表示分页,0表示不分页
     */
    @ApiModelProperty(hidden = true)
    private String paging = DEF_PAGING;

    @ApiModelProperty(hidden = true)
    private String sorter;

    @ApiModelProperty(value = "页码")
    private Integer pageNo = PAGE_START;

//    @NotNull(message = "pageSize不能为空")
    @ApiModelProperty(value = "每页个数")
    private Integer pageSize = DEF_PAGESIZE;



    @ApiModelProperty(hidden = true)
    public Integer getCurrentIndex() {
        return getPageNo() == null || getPageSize() == null
                || getPageNo() <= 0 ? 0 : (getPageNo() - 1)
                * getPageSize();
    }
}
