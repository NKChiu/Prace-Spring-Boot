package com.springboot.prac.testap.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("table1顯示用多筆物件")
public class TestTable1View {

    @ApiModelProperty(value = "table1顯示用多筆物件參數")
    private List<TestTable1Dto> testTable1DtoList;
}
