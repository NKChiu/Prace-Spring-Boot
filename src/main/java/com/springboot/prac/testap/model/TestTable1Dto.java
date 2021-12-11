package com.springboot.prac.testap.model;

import com.springboot.prac.testap.entity.TestTableEnt1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.modelmapper.PropertyMap;

@Data
@ApiModel("table1顯示用單筆物件")
public class TestTable1Dto {

    @ApiModelProperty("排序")
    private int number;
    @ApiModelProperty("參數1")
    private String variable1;
    @ApiModelProperty("參數2")
    private String variable2;

    public PropertyMap<TestTableEnt1, TestTable1Dto> propertyMap(){
        return new PropertyMap<TestTableEnt1, TestTable1Dto>() {
            @Override
            protected void configure() {
                map(source.getId(), destination.getNumber());
                map(source.getVar1(), destination.getVariable1());
                map(source.getVar2(), destination.getVariable2());
            }
        };
    }
}
