package com.springboot.prac.testap.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("新增tabl1資訊")
@Data
public class TestTable1Input {

    @ApiModelProperty(notes = "參數1", required = true, example = "var111")
    private String variable1;
    @ApiModelProperty(notes = "參數2", required = true, example = "var222")
    private String variable2;

}
