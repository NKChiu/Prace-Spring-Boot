package com.springboot.prac.testap.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.springboot.prac.testap.dao.TestTableEnt1Dao;
import com.springboot.prac.testap.dao.TestTableEnt2Dao;
import com.springboot.prac.testap.entity.TestTableEnt1;
import com.springboot.prac.testap.entity.TestTableEnt2;
import com.springboot.prac.testap.model.TestTable1Dto;
import com.springboot.prac.testap.model.TestTable1Input;
import com.springboot.prac.testap.model.TestTable1View;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
@Api(tags = "測試API")
public class TestTableController {

    @Autowired
    private TestTableEnt1Dao testTableEnt1Dao;
    @Autowired
    private TestTableEnt2Dao testTableEnt2Dao;

    /**
     * @deescription from h2 default schema
     */
    @ApiOperation("取得table1所有資訊")
    @GetMapping(path = "/getTestTableEnt1All")
    @ResponseBody
    public TestTable1View getTestTableEnt1All(){
        TestTable1View testTable1View = new TestTable1View();
        // call dao
        List<TestTableEnt1> testTableEnt1List = testTableEnt1Dao.findAll();
        // arrange
        List<TestTable1Dto> testTable1DtoList = this.arrangeTestTable1DtoList(testTableEnt1List);
        testTable1View.setTestTable1DtoList(testTable1DtoList);

        return testTable1View;
    }

    @ApiOperation("新增table1資訊")
    @PostMapping(path = "/addTestTableEnt1")
    @ResponseBody
    public TestTable1Dto addTestTableEnt1(@ApiParam(required = true, value = "add") TestTable1Input testTable1Input){

        TestTableEnt1 testTableEnt1 = new TestTableEnt1();
        testTableEnt1.setVar1(testTable1Input.getVariable1());
        testTableEnt1.setVar2(testTable1Input.getVariable2());

        TestTableEnt1 testTableEnt11 = testTableEnt1Dao.save(testTableEnt1);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new TestTable1Dto().propertyMap());
        TestTable1Dto testTable1Dto = modelMapper.map(testTableEnt11, TestTable1Dto.class);

        return testTable1Dto;
    }

    /**
     * @deescription from h2 customer schema
     */
    @ApiOperation("取得table2所有資訊")
    @GetMapping(path = "/getTestTableEnt2All")
    @ResponseBody
    public List<TestTableEnt2> getTestTableEnt2All(){
        List<TestTableEnt2> all = testTableEnt2Dao.findAll();
        return all;
    }

    private List<TestTable1Dto> arrangeTestTable1DtoList(List<TestTableEnt1> testTableEnt1List){
        List<TestTable1Dto> testTable1DtoList = new ArrayList<TestTable1Dto>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new TestTable1Dto().propertyMap());
        testTableEnt1List.forEach(en1->{
            TestTable1Dto testTable1Dto = modelMapper.map(en1, TestTable1Dto.class);
            testTable1DtoList.add(testTable1Dto);
        });
        return testTable1DtoList;
    }
}
