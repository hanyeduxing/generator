package ${basePackage}.req;

import ${basePackage}.common.req.BaseReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel(description = "${remarks}列表查询条件")
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ${modelName}Req extends BaseReq {

}
